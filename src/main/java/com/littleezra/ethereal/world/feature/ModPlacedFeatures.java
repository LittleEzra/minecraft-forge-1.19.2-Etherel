package com.littleezra.ethereal.world.feature;

import com.littleezra.ethereal.Ethereal;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


import java.util.List;

public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Ethereal.MODID);

    // Placed Features

    public static final RegistryObject<PlacedFeature> GILDBLOSSOM_PLACED = PLACED_FEATURES.register("gildblossom_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.GILDBLOSSOM.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(16),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));

    public static final RegistryObject<PlacedFeature> ETHEREAL_STAR_PLACED = PLACED_FEATURES.register("ethereal_star_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.ETHEREAL_STAR.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(7),
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(5), VerticalAnchor.absolute(15)), BiomeFilter.biome())));

    public static void register(IEventBus eventBus){
        PLACED_FEATURES.register(eventBus);
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier placementModifier, PlacementModifier placementModifier1) {
        return List.of(placementModifier, InSquarePlacement.spread(), placementModifier1, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int i, PlacementModifier placementModifier) {
        return orePlacement(CountPlacement.of(i), placementModifier);
    }

    private static List<PlacementModifier> rareOrePlacement(int i, PlacementModifier placementModifier) {
        return orePlacement(RarityFilter.onAverageOnceEvery(i), placementModifier);
    }
}
