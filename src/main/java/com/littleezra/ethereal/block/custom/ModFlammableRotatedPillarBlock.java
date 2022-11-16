package com.littleezra.ethereal.block.custom;

import com.littleezra.ethereal.block.ModBlocks;
import com.littleezra.ethereal.item.ModItems;
import com.littleezra.ethereal.item.custom.VerdantSapItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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

}
