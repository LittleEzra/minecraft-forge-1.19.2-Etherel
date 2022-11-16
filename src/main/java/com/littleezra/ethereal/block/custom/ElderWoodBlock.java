package com.littleezra.ethereal.block.custom;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.player.PlayerFairyAggressor;
import com.littleezra.ethereal.player.PlayerFairyAggressorProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.util.LazyOptional;

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

        if (state.getValue(NATURAL)){
            LazyOptional<PlayerFairyAggressor> lazyOptional = player.getCapability(PlayerFairyAggressorProvider.PLAYER_FAIRY_AGGRESSOR);

            if (lazyOptional.isPresent()){
                PlayerFairyAggressor aggressor = lazyOptional.orElseGet(() ->{
                    return null;
                });

                aggressor.setHurtOak(true);
                player.sendSystemMessage(Component.literal("Oops, hurt the elder oak! NOW DIE."));
                Ethereal.FAIRY_AGGRESSOR.trigger(Ethereal.getServerPlayer(player));
            }
        }

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }
}
