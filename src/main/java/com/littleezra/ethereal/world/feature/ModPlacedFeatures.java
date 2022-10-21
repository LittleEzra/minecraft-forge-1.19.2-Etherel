package com.littleezra.ethereal.world.feature;

import com.littleezra.ethereal.Ethereal;
import net.minecraft.core.Registry;
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

    public static final RegistryObject<PlacedFeature> ETHEREAL_SAP_PLACED = PLACED_FEATURES.register("ethereal_sap_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.ETHEREAL_SAP.getHolder().get(),
                    commonOrePlacement(7, //Veins per Chunk
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.absolute(30)))));


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
