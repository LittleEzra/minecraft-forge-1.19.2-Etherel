package com.littleezra.ethereal.world.feature;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.block.ModBlocks;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


import java.util.List;


public class ModConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Ethereal.MODID);

    // Adding ores
    public  static  final Supplier<List<OreConfiguration.TargetBlockState>> ENDSTONE_ETHEREAL_SAP = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.END_STONE), ModBlocks.ETHEREAL_SAP_BLOCK.get().defaultBlockState())));

    // Adding ore feature
    public static final RegistryObject<ConfiguredFeature<?, ?>> ETHEREAL_SAP = CONFIGURED_FEATURES.register("ethereal_sap",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ENDSTONE_ETHEREAL_SAP.get(), 7)));

    public static void register(IEventBus eventBus){
        CONFIGURED_FEATURES.register(eventBus);
    }
}
