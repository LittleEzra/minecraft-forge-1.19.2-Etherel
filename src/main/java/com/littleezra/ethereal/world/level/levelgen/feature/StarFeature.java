package com.littleezra.ethereal.world.level.levelgen.feature;

import com.littleezra.ethereal.world.level.levelgen.feature.configurations.StarConfiguration;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class StarFeature extends Feature<StarConfiguration> {
    public StarFeature(Codec<StarConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<StarConfiguration> context) {
        return false;
    }
}
