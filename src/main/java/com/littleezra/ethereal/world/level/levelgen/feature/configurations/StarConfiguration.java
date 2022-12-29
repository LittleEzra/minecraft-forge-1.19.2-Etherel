package com.littleezra.ethereal.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class StarConfiguration implements FeatureConfiguration {

    public static final Codec<StarConfiguration> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockStateProvider.CODEC.fieldOf("block_provider").forGetter((starConfiguration -> {
            return starConfiguration.blockProvider;
        }))).apply(instance, StarConfiguration::new);
    });

    public final BlockStateProvider blockProvider;

    public StarConfiguration(BlockStateProvider blockProvider){
        this.blockProvider = blockProvider;
    }

    public static class StarConfigurationBuilder {
        BlockStateProvider blockProvider;

        public StarConfigurationBuilder(BlockStateProvider blockProvider){
            this.blockProvider = blockProvider;
        }

        public StarConfigurationBuilder block(BlockStateProvider blockProvider){
            this.blockProvider = blockProvider;
            return this;
        }

        public StarConfiguration build() {
            return new StarConfiguration(this.blockProvider);
        }
    }
}
