package com.littleezra.ethereal.world.feature;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.block.ModBlocks;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.littleezra.ethereal.world.level.levelgen.feature.configurations.StarConfiguration;
import com.littleezra.ethereal.world.level.levelgen.feature.placers.LargeFilledTrunkPlacer;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


import java.util.List;


public class ModConfiguredFeatures {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Ethereal.MODID);

    public static final RegistryObject<ConfiguredFeature<?, ?>> ETHEREAL_STAR = CONFIGURED_FEATURES.register("ethereal_star",
            () -> new ConfiguredFeature<>(ModFeatures.STAR.get(), new StarConfiguration.StarConfigurationBuilder(
                    BlockStateProvider.simple(ModBlocks.ETHEREAL_SAP_BLOCK.get()))
                   .build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> RICH_TREE = CONFIGURED_FEATURES.register("rich_tree", () ->
            new ConfiguredFeature<>(Feature.TREE, createRich().build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> GILDBLOSSOM = CONFIGURED_FEATURES.register("gildblossom",
            () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(12, 2, 2,
                    PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.GILDBLOSSOM.get()))))));

    private static TreeConfiguration.TreeConfigurationBuilder filledTrunk(Block trunkBlock, Block fillBlock, Block leavesBlock, int baseHeight, int randA, int randB, int radius){
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(trunkBlock),
                new LargeFilledTrunkPlacer(baseHeight, randA, randB)
                        .filling(BlockStateProvider.simple(fillBlock), 1, 3),
                BlockStateProvider.simple(leavesBlock),
                new BlobFoliagePlacer(ConstantInt.of(radius), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1) );
    }

    private static TreeConfiguration.TreeConfigurationBuilder createRich() {
        return filledTrunk(ModBlocks.RICH_LOG.get(), ModBlocks.VERDANT_SAP_BLOCK.get(), ModBlocks.RICH_LEAVES.get(), 10, 6, 3, 2).ignoreVines();
    }

    public static void register(IEventBus eventBus){
        CONFIGURED_FEATURES.register(eventBus);
    }
}
