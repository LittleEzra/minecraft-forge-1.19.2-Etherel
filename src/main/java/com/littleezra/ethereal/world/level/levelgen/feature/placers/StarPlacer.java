package com.littleezra.ethereal.world.level.levelgen.feature.placers;

import com.littleezra.ethereal.world.level.levelgen.feature.configurations.EtherealSapConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public class StarPlacer {

    public void placeStar(BlockPos rootPos, BlockState state, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, EtherealSapConfiguration configuration)
    {
        List<BlockPos> positions = new ArrayList<>(List.of());

        positions.add(rootPos);

        int size = getRandomNumber(1, 5);

        for (int i = 0; i < size; i++)
        {
            List<BlockPos> newPositions = new ArrayList<>(List.of());

            for (int pI = 0; pI < positions.size(); pI++){
                BlockPos position = positions.get(pI);
                BlockPos[] neighbours = getNeighbours(position);
                for (BlockPos neighbour : neighbours) {
                    if (!positions.contains(neighbour)) {
                        newPositions.add(neighbour);
                    }
                }
            }

            positions.addAll(newPositions);
        }

        for (BlockPos position : positions) {
            tryPlaceBlock(biConsumer, position, state, configuration, randomSource);
        }
    }

    public int getRandomNumber(int min, int max){
        return (int)((Math.random() * (max - min)) + min);
    }

    public BlockPos[] getNeighbours(BlockPos pos)
    {
        BlockPos[] positions = new BlockPos[6];

        positions[0] = new BlockPos(pos.getX(), pos.getY() + 1f, pos.getZ());
        positions[1] = new BlockPos(pos.getX(), pos.getY() - 1f, pos.getZ());
        positions[2] = new BlockPos(pos.getX() + 1f, pos.getY(), pos.getZ());
        positions[3] = new BlockPos(pos.getX() - 1f, pos.getY(), pos.getZ());
        positions[4] = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1f);
        positions[5] = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1f);

        return positions;
    }

    private static void tryPlaceBlock(BiConsumer<BlockPos, BlockState> biConsumer, BlockPos pos, BlockState state, EtherealSapConfiguration configuration, RandomSource randomSource)
    {
        if (configuration.blockProvider.getState(randomSource, pos).isAir()){
            biConsumer.accept(pos, state);
        }
    }
}
