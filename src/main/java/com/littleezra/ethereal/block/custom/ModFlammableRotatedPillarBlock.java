package com.littleezra.ethereal.block.custom;

import com.littleezra.ethereal.block.ModBlocks;
import com.littleezra.ethereal.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolAction;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

public class ModFlammableRotatedPillarBlock extends RotatedPillarBlock
{
    public ModFlammableRotatedPillarBlock(Properties p_55926_)
    {
        super(p_55926_);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 3;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 3;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (context.getItemInHand().getItem() instanceof AxeItem){
            if (state.is(ModBlocks.RICH_LOG.get())){
                return ModBlocks.STRIPPED_RICH_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            if (state.is(ModBlocks.RICH_WOOD.get())){
                return ModBlocks.STRIPPED_RICH_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (state.is(ModBlocks.STRIPPED_RICH_LOG.get()) && !level.isClientSide() && hand == InteractionHand.MAIN_HAND && player.getItemInHand(hand).getItem() instanceof AxeItem){
            dropSap(pos, level, state);
            level.setBlock(pos, Blocks.STRIPPED_SPRUCE_LOG.defaultBlockState().setValue(AXIS, state.getValue(AXIS)), 3);

            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    public void dropSap(BlockPos pos, Level level, BlockState state){
        if (level instanceof ServerLevel) {

            ItemStack drop = new ItemStack(ModItems.VERDANT_SAP.get(), 1);

            popResource(level, pos, drop);

            state.spawnAfterBreak((ServerLevel)level, pos, ItemStack.EMPTY, true);
        }
    }
}
