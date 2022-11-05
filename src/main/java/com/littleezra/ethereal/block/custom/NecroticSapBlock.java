package com.littleezra.ethereal.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class NecroticSapBlock extends SapBlock{

    public NecroticSapBlock(Properties p_49795_, Vec3 stuck) {
        super(p_49795_, stuck);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos blockPos, Entity entity) {
        super.entityInside(state, level, blockPos, entity);

        if (entity instanceof LivingEntity living){
            living.hurt(DamageSource.MAGIC, 1);
        }
    }
}
