package com.littleezra.ethereal.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;

public class EtherealTorchBlock extends TorchBlock {

    public EtherealTorchBlock(Properties p_57491_, ParticleOptions p_57492_) {
        super(p_57491_, p_57492_);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 15;
    }
}
