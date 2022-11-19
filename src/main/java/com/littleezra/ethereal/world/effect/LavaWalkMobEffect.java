package com.littleezra.ethereal.world.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class LavaWalkMobEffect extends MobEffect {
    protected LavaWalkMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        BlockPos below = entity.blockPosition().below();
        Level level = entity.getLevel();

        double x = entity.getDeltaMovement().x;
        double y = 0;
        double z = entity.getDeltaMovement().z;

        if (level.getBlockState(below).is(Blocks.LAVA)){
            entity.setDeltaMovement(x, y, z);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
