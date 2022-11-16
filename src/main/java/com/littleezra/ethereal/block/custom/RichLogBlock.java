package com.littleezra.ethereal.block.custom;

import com.littleezra.ethereal.event.ModEvents;
import com.littleezra.ethereal.item.ModItems;
import com.littleezra.ethereal.item.custom.VerdantSapItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RichLogBlock extends ModFlammableRotatedPillarBlock{
    public RichLogBlock(Properties p_55926_) {
        super(p_55926_);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        // level.gameEvent(player, GameEvent.FLUID_PICKUP, blockpos);

        //level.gameEvent(player, GameEvent.);

        if (!level.isClientSide && hand == InteractionHand.MAIN_HAND && player.getItemInHand(hand).getItem() instanceof BucketItem){

            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, player.getItemInHand(hand));
            }

            return getOak(state).map((blockState) ->{

                dropSap(pos, level, hitResult.getDirection());
                level.setBlock(pos, blockState.setValue(AXIS, state.getValue(AXIS)), 3);

                return InteractionResult.SUCCESS;
            }).orElse(InteractionResult.PASS);
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    public void dropSap(BlockPos pos, Level level, Direction direction)
    {
        ItemStack drop = new ItemStack(ModItems.VERDANT_SAP.get(), 1);

        popResourceFromFace(level, pos, direction, drop);
    }
    public static Optional<BlockState> getOak(BlockState state) {
        return Optional.ofNullable(VerdantSapItem.OAK_VERSION.get().get(state.getBlock())).map((block) -> {
            return block.withPropertiesOf(state);
        });
    }
}
