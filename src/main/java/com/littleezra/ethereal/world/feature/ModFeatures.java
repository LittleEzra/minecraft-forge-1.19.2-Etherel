package com.littleezra.ethereal.world.feature;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.world.level.levelgen.feature.StarFeature;
import com.littleezra.ethereal.world.level.levelgen.feature.configurations.StarConfiguration;
import com.littleezra.ethereal.world.level.levelgen.feature.placers.StarPlacer;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, Ethereal.MODID);

    public static final RegistryObject<Feature<StarConfiguration>> STAR =
            FEATURES.register("star", () -> new StarPlacer(StarConfiguration.CODEC));

    public static void register(IEventBus eventBus){
        FEATURES.register(eventBus);
    }
}
