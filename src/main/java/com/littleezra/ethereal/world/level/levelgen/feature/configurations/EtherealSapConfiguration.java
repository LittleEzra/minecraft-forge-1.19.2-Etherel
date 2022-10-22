package com.littleezra.ethereal.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class EtherealSapConfiguration implements FeatureConfiguration {

    public final BlockStateProvider blockProvider;

    public EtherealSapConfiguration (BlockStateProvider blockProvider){
        this.blockProvider = blockProvider;
    }

    public static class EtherealSapConfigurationBuilder{
        BlockStateProvider blockProvider;

        public EtherealSapConfigurationBuilder(BlockStateProvider blockProvider){
            this.blockProvider = blockProvider;
        }

        public EtherealSapConfiguration.EtherealSapConfigurationBuilder block(BlockStateProvider blockProvider){
            this.blockProvider = blockProvider;
            return this;
        }
    }
}
