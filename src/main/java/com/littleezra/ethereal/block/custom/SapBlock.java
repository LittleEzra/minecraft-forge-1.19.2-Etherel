package com.littleezra.ethereal.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SapBlock extends GlassBlock
{
    private Vec3 stuckInside;

    public SapBlock(Properties p_49795_, Vec3 stuck) {
        super(p_49795_);
        stuckInside = stuck;
    }

    public void entityInside(BlockState p_58180_, Level p_58181_, BlockPos p_58182_, Entity p_58183_) {
        p_58183_.makeStuckInBlock(p_58180_, stuckInside);
    }
}
