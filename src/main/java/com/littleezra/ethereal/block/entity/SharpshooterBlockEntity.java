package com.littleezra.ethereal.block.entity;

import com.littleezra.ethereal.block.ModBlocks;
import com.littleezra.ethereal.screen.SharpshooterMenu;
import com.mojang.math.Quaternion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class SharpshooterBlockEntity extends BaseContainerBlockEntity implements MenuProvider {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private final ItemStackHandler itemHandler = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private static final Predicate<Item> SHOOTABLE_PREDICATE = (item) -> {
      return item != null && (item.asItem() == Items.ARROW ||
              item.asItem() == Items.SPECTRAL_ARROW ||
              item.asItem() == Items.TIPPED_ARROW );
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;

    public SharpshooterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SHARPSHOOTER.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index){
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Sharpshooter");
    }

    @Override
    protected Component getDefaultName() {
        return null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new DispenserMenu(id, inventory, this);
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new DispenserMenu(id, inventory, this);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState state, SharpshooterBlockEntity blockEntity)
    {
        if (level.isClientSide()){
            return;
        }

        Quaternion rotation = state.getValue(FACING).getRotation();

        BlockPos offset = BlockPos.ZERO;

        switch (state.getValue(FACING)){
            case SOUTH -> {
                offset = blockPos.offset(new Vec3i(0, 0, 5));
            }
            case WEST -> {
                offset = blockPos.offset(new Vec3i(-5, 0, 0));
            }
            case EAST -> {
                offset = blockPos.offset(new Vec3i(5, 0, 0));
            }
            default -> {
                offset = blockPos.offset(new Vec3i(0, 0, -5));
            }
        }

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(offset, blockPos));

        if (entities.size() > 0){
            System.out.println(entities.size() + " entities found!");
            shoot(level, blockPos, state, blockEntity);
        }
    }

    public static void shoot(Level level, BlockPos blockPos, BlockState state, SharpshooterBlockEntity entity){

        SimpleContainer inventory= new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++){
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        for (int i = 0; i < inventory.getContainerSize(); i++){
            if (inventory.getItem(i).getCount() > 0 && inventory.getItem(i).getItem() == SHOOTABLE_PREDICATE){

                ItemStack itemStack = inventory.getItem(i);
                entity.itemHandler.getStackInSlot(i).shrink(1);

                System.out.println("Shooting a " + itemStack.getDisplayName() + " in slot " + i + " with a size of " + itemStack.getCount());
            }
        }
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int i) {
        return itemHandler.getStackInSlot(i);
    }

    @Override
    public ItemStack removeItem(int i, int count) {
        return itemHandler.extractItem(i, count, true);
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_18951_) {
        return null;
    }

    @Override
    public void setItem(int p_18944_, ItemStack p_18945_) {

    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return false;
    }

    @Override
    public void clearContent() {

    }
}
