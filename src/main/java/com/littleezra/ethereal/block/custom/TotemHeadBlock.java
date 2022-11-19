package com.littleezra.ethereal.block.custom;

import com.littleezra.ethereal.block.ModBlocks;
import com.littleezra.ethereal.entity.ModEntityTypes;
import com.littleezra.ethereal.entity.custom.TotemGolem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class TotemHeadBlock extends HorizontalDirectionalBlock
{
    @Nullable
    private BlockPattern golemFull;

    private static final Predicate<BlockState> SPITE_PREDICATE = (p_51396_) -> {
        return p_51396_ != null && (p_51396_.is(ModBlocks.SPITE_TOTEM.get()));
    };
    private static final Predicate<BlockState> SURPRISE_PREDICATE = (p_51396_) -> {
        return p_51396_ != null && (p_51396_.is(ModBlocks.SURPRISE_TOTEM.get()));
    };
    private static final Predicate<BlockState> SOBRIETY_PREDICATE = (p_51396_) -> {
        return p_51396_ != null && (p_51396_.is(ModBlocks.SOBRIETY_TOTEM.get()));
    };

    public TotemHeadBlock(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_54779_) {
        return this.defaultBlockState().setValue(FACING, p_54779_.getHorizontalDirection().getOpposite());
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

    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean p_51391_) {
        if (!state2.is(state.getBlock())) {
            this.trySpawnGolem(level, pos);
        }
    }

    private void trySpawnGolem(Level level, BlockPos pos)
    {
        BlockPattern.BlockPatternMatch blockpattern$blockpatternmatch = this.getOrCreateGolemFull().find(level, pos);
        if (blockpattern$blockpatternmatch != null) {
            for(int i = 0; i < this.getOrCreateGolemFull().getHeight(); ++i) {
                BlockInWorld blockinworld = blockpattern$blockpatternmatch.getBlock(0, i, 0);
                level.setBlock(blockinworld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                level.levelEvent(2001, blockinworld.getPos(), Block.getId(blockinworld.getState()));
            }

            TotemGolem totemGolem = ModEntityTypes.TOTEM_GOLEM.get().create(level);
            BlockPos blockpos1 = blockpattern$blockpatternmatch.getBlock(0, 2, 0).getPos();
            totemGolem.moveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.05D, (double)blockpos1.getZ() + 0.5D, 0.0F, 0.0F);
            level.addFreshEntity(totemGolem);

            for(ServerPlayer serverplayer : level.getEntitiesOfClass(ServerPlayer.class, totemGolem.getBoundingBox().inflate(5.0D))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, totemGolem);
            }

            for(int l = 0; l < this.getOrCreateGolemFull().getHeight(); ++l) {
                BlockInWorld blockinworld3 = blockpattern$blockpatternmatch.getBlock(0, l, 0);
                level.blockUpdated(blockinworld3.getPos(), Blocks.AIR);
            }
        }
    }

    private BlockPattern getOrCreateGolemFull() {
        if (this.golemFull == null) {
            this.golemFull = BlockPatternBuilder.start().aisle("^", "#", "/").where('^', BlockInWorld.hasState(SPITE_PREDICATE)).where('#', BlockInWorld.hasState(SURPRISE_PREDICATE)).where('/', BlockInWorld.hasState(SOBRIETY_PREDICATE)).build();
        }

        return this.golemFull;
    }
}
