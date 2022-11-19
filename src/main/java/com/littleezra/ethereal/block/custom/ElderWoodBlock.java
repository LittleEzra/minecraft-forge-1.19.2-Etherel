package com.littleezra.ethereal.block.custom;

import com.littleezra.ethereal.entity.custom.IFairy;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;

public class ElderWoodBlock extends ModFlammableRotatedPillarBlock
{
    public static final BooleanProperty NATURAL = BooleanProperty.create("natural");
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        super.createBlockStateDefinition(blockStateBuilder);
        blockStateBuilder.add(NATURAL);
    }

    public ElderWoodBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(NATURAL, false)
        );
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {

        if (state.getValue(NATURAL) && IFairy.playerGetAggressor(player) != null){
            IFairy.playerGetAggressor(player).setHurtOak(true);
        }

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
