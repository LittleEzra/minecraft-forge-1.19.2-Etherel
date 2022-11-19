package com.littleezra.ethereal.world.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public class WaterStriderMobEffect extends MobEffect {
    protected WaterStriderMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    private static final Predicate<BlockState> isValidLiquid = (blockState) -> {
        return blockState.is(Blocks.WATER) || blockState.is(Blocks.LAVA);
    };

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

        BlockPos below = entity.blockPosition().below();
        Level level = entity.level;

        BlockState belowState = level.getBlockState(getOnPos(entity));

        if (isValidLiquid.test(belowState))
        {
            double x = entity.getDeltaMovement().x;
            double y = 0;
            double z = entity.getDeltaMovement().z;

            entity.setDeltaMovement(x, y, z);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public BlockPos getOnPos(LivingEntity entity) {
        return this.getOnPos(entity, 1.1E-5F);
    }

    private BlockPos getOnPos(LivingEntity entity, float range) {
        int i = Mth.floor(entity.position().x);
        int j = Mth.floor(entity.position().y - (double)range);
        int k = Mth.floor(entity.position().z);
        BlockPos blockpos = new BlockPos(i, j, k);
        if (entity.level.isEmptyBlock(blockpos)) {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = entity.level.getBlockState(blockpos1);
            if (blockstate.collisionExtendsVertically(entity.level, blockpos1, entity)) {
                return blockpos1;
            }
        }

        return blockpos;
    }
}
