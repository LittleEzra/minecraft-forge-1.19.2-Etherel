package com.littleezra.ethereal.world.level.levelgen.feature.placers;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class LargeFilledTrunkPlacer extends TrunkPlacer
{
    private BlockPattern circle2;

    public static final Codec<LargeFilledTrunkPlacer> CODEC = RecordCodecBuilder.create((p_70261_) -> {
        return trunkPlacerParts(p_70261_).apply(p_70261_, LargeFilledTrunkPlacer::new);
    });

    public LargeFilledTrunkPlacer(int p_70268_, int p_70269_, int p_70270_) {
        super(p_70268_, p_70269_, p_70270_);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return TrunkPlacerType.GIANT_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, int size, BlockPos pos, TreeConfiguration configuration) {
        setDirtAt(levelReader, biConsumer, randomSource, pos.below(), configuration);

        for(int i = 0; i < size; ++i) {
            this.placeLog(levelReader, biConsumer, randomSource, pos.above(i), configuration);
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(size), 0, false));
    }


}
