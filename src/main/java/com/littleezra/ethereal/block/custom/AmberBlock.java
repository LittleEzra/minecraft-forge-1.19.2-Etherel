package com.littleezra.ethereal.block.custom;

import com.littleezra.ethereal.block.entity.AmberBlockEntity;
import com.littleezra.ethereal.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AmberBlock extends BaseEntityBlock {
    public AmberBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AmberBlockEntity(ModBlockEntities.AMBER_BLOCK.get(), pos, state);
    }
}
