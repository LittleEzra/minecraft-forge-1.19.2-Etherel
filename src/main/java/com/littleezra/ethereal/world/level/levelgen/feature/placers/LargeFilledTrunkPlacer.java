package com.littleezra.ethereal.world.level.levelgen.feature.placers;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class LargeFilledTrunkPlacer extends TrunkPlacer
{
    public static final Codec<LargeFilledTrunkPlacer> CODEC = RecordCodecBuilder.create((p_70261_) -> {
        return trunkPlacerParts(p_70261_).apply(p_70261_, LargeFilledTrunkPlacer::new);
    });

    private BlockStateProvider fillBlock;
    private int minFill;
    private int maxFill;

    private final List<BlockPos> circleR1;
    private final List<BlockPos> circleR2;

    public LargeFilledTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
        circleR2 = generateCircle(2);
        circleR1 = generateCircle(1);
        circleR1.addAll(ImmutableList.of(
                new BlockPos(1, 0, 1),
                new BlockPos(-1, 0, 1),
                new BlockPos(1, 0, -1),
                new BlockPos(-1, 0, -1)));
    }

    public TrunkPlacer filling(BlockStateProvider fillBlock, int minFill, int maxFill){
        this.fillBlock = fillBlock;
        this.minFill = minFill;
        this.maxFill = maxFill;
        return this;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return TrunkPlacerType.GIANT_TRUNK_PLACER;
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource randomSource, int size, BlockPos pos, TreeConfiguration configuration) {
        int offset = Math.round(size * 0.5f + randomSource.nextIntBetweenInclusive(-1, 2));
        int fill = randomSource.nextIntBetweenInclusive(minFill, maxFill);

        for(int i = 0; i < size; ++i) {
            if (i == 0){
                this.placeLog(levelReader, biConsumer, randomSource, pos, configuration);
                circleR1.forEach(blockPos -> {
                    this.placeLog(levelReader, biConsumer, randomSource, addBlockPos(blockPos, pos), configuration);
                });
            } else{
                for (BlockPos blockpos : (i < offset) ? circleR2 : circleR1){
                    this.placeLog(levelReader, biConsumer, randomSource, addBlockPos(blockpos, pos.above(i)), configuration);
                }
                if (i < fill + 1){
                    this.placeFill(levelReader, biConsumer, randomSource, pos.above(i), i < offset ? 2 : 1);
                }
            }
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(size + 2), 1, false));
    }

    protected boolean placeLog(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource random, BlockPos pos, TreeConfiguration config, boolean forcePlace) {
        return this.placeLog(reader, biConsumer, random, pos, config, Function.identity(),forcePlace);
    }

    protected boolean placeLog(LevelSimulatedReader levelReader, BiConsumer<BlockPos, BlockState> biConsumer, RandomSource random, BlockPos pos, TreeConfiguration config, Function<BlockState, BlockState> function, boolean forcePlace) {
        if (this.validTreePos(levelReader, pos) || forcePlace) {
            biConsumer.accept(pos, function.apply(config.trunkProvider.getState(random, pos)));
            return true;
        } else {
            return false;
        }
    }

    public List<BlockPos> generateCircle(int radius){
        int x = 0;
        int y = radius;

        List<BlockPos> firstOctant = new ArrayList<>(List.of(new BlockPos(x, 0, y)));

        while (x < y){
            if (RE(x + 1, y - 1, radius) < RE(x + 1, y, radius)){
                x += 1;
                y -= 1;
                firstOctant.add(new BlockPos(x, 0, y));
            } else{
                x += 1;
                firstOctant.add(new BlockPos(x, 0, y));
            }
        }

        List<BlockPos> halfCircle = new ArrayList<>(List.of());
        halfCircle.addAll(firstOctant);

        List<BlockPos> fullCircle = new ArrayList<>(List.of());

        for (BlockPos pos : firstOctant){
            halfCircle.add(pos);
        }
        for (BlockPos pos : firstOctant){
            halfCircle.add(new BlockPos(pos.getZ(), 0, pos.getX()));
        }
        for (BlockPos pos : firstOctant){
            halfCircle.add(new BlockPos(pos.getX(), 0, -pos.getZ()));
        }
        for (BlockPos pos : firstOctant){
            halfCircle.add(new BlockPos(-pos.getX(), 0, pos.getZ()));
        }

        for (BlockPos pos : halfCircle){
            fullCircle.add(pos);
            fullCircle.add(new BlockPos(-pos.getX(), 0, pos.getZ()));
        }

        return fullCircle;
    }

    int RE(int x, int y, int radius){
        return Math.abs((x * x) + (y * y) - (radius * radius));
    }

    void placeFill(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> consumer, RandomSource source, BlockPos pos, int radius){
        placeFillBlock(reader, consumer, source, pos);
        circleR1.forEach(blockPos -> {
            this.placeFillBlock(reader, consumer, source, addBlockPos(blockPos, pos));
        });
    }

    boolean placeFillBlock(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> consumer, RandomSource source, BlockPos pos){
        return placeFillBlock(reader, consumer, source, pos, Function.identity());
    }

    boolean placeFillBlock(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> consumer, RandomSource source, BlockPos pos,
                           Function<BlockState, BlockState> function){
        if (this.validTreePos(reader, pos)) {
            consumer.accept(pos, function.apply(fillBlock.getState(source, pos)));
            return true;
        } else {
            return false;
        }
    }

    private BlockPos addBlockPos(BlockPos a, BlockPos b){
        return new BlockPos(
                a.getX() + b.getX(),
                a.getY() + b.getY(),
                a.getZ() + b.getZ());
    }
}
