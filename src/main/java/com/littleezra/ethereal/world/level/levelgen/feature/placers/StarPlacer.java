package com.littleezra.ethereal.world.level.levelgen.feature.placers;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.littleezra.ethereal.world.level.levelgen.feature.configurations.StarConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

import java.util.*;
import java.util.function.BiConsumer;

public class StarPlacer extends Feature<StarConfiguration>{

    public StarPlacer(Codec<StarConfiguration> codec) {
        super(codec);
    }

    public List<BlockPos> placeStar(LevelSimulatedReader reader, BlockPos rootPos, BlockState state, BiConsumer<BlockPos, BlockState> biConsumer, int size, RandomSource randomSource, StarConfiguration configuration)
    {
        List<BlockPos> positions = new ArrayList<>(List.of());

        positions.add(rootPos);

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

        positions.forEach((position) -> {
            tryPlaceBlock(reader, biConsumer, position, state, configuration, randomSource);
        });

        System.out.println("Generated " + positions.size() + " positions with a size of " + size);

        return positions;
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

    private static void tryPlaceBlock(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> biConsumer, BlockPos pos, BlockState state, StarConfiguration configuration, RandomSource randomSource)
    {
        if (reader.isStateAtPosition(pos, blockState -> {
            return blockState.isAir();
        })){
            biConsumer.accept(pos, state);
        }
    }

    private boolean doPlace(WorldGenLevel worldGenLevel, RandomSource randomSource, int size, BlockPos pos, BiConsumer<BlockPos, BlockState> consumer, StarConfiguration configuration){
        int minHeight = pos.getY();
        int maxHeight = pos.getY() + size + 1;
        if (minHeight >= worldGenLevel.getMinBuildHeight() + 1 && maxHeight <= worldGenLevel.getMaxBuildHeight()) {
            placeStar(worldGenLevel, pos, configuration.blockProvider.getState(randomSource, pos), consumer, size, randomSource, configuration);
            return true;
        }
        return false;
    }

    @Override
    public boolean place(FeaturePlaceContext<StarConfiguration> context) {
        WorldGenLevel worldgenlevel = context.level();
        RandomSource randomsource = context.random();
        Set<BlockPos> set = Sets.newHashSet();

        BiConsumer<BlockPos, BlockState> consumer = (blockpos, state) -> {
            set.add(blockpos.immutable());
            worldgenlevel.setBlock(blockpos, state, 19);
        };

        int size = getRandomNumber(1, 5);

        boolean flag = this.doPlace(worldgenlevel, randomsource, size, context.origin(), consumer, context.config());
        if (flag && (!set.isEmpty())) {
            return BoundingBox.encapsulatingPositions(Iterables.concat(set)).map((boundingBox) -> {
                DiscreteVoxelShape discretevoxelshape = updateStar(worldgenlevel, boundingBox, set);
                StructureTemplate.updateShapeAtEdge(worldgenlevel, 3, discretevoxelshape, boundingBox.minX(), boundingBox.minY(), boundingBox.minZ());
                return true;
            }).orElse(false);
        } else {
            return false;
        }
    }

    private DiscreteVoxelShape updateStar(WorldGenLevel worldGenLevel, BoundingBox boundingBox, Set<BlockPos> positions){
        DiscreteVoxelShape shape = new BitSetDiscreteVoxelShape(boundingBox.getXSpan(), boundingBox.getYSpan(), boundingBox.getZSpan());

        for (BlockPos pos : positions) {
            if (boundingBox.isInside(pos)) {
                shape.fill(pos.getX() - boundingBox.minX(), pos.getY() - boundingBox.minY(), pos.getZ() - boundingBox.minZ());
            }
        }

        return shape;
    }
}
