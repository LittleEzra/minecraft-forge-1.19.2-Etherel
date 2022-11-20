package com.littleezra.ethereal.world.feature;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.block.ModBlocks;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.littleezra.ethereal.world.level.levelgen.feature.configurations.EtherealSapConfiguration;
import com.littleezra.ethereal.world.level.levelgen.feature.placers.StarPlacer;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
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

    // Adding ores
    public  static  final Supplier<List<OreConfiguration.TargetBlockState>> ENDSTONE_ETHEREAL_SAP = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(Blocks.END_STONE), ModBlocks.ETHEREAL_SAP_BLOCK.get().defaultBlockState())));

    // Adding ore feature
    public static final RegistryObject<ConfiguredFeature<?, ?>> ETHEREAL_SAP = CONFIGURED_FEATURES.register("ethereal_sap",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ENDSTONE_ETHEREAL_SAP.get(), 7)));

    //public static final RegistryObject<ConfiguredFeature<?, ?>> ETHEREAL_STAR = CONFIGURED_FEATURES.register("ethereal_star", () -> new ConfiguredFeature<>(Feature.DELTA_FEATURE,
    //       new EtherealSapConfiguration.EtherealSapConfigurationBuilder(
    //                BlockStateProvider.simple(ModBlocks.ETHEREAL_SAP_BLOCK.get()))
    //               .build()));

    public static final RegistryObject<ConfiguredFeature<?, ?>> RICH_TREE = CONFIGURED_FEATURES.register("rich_tree", () ->
            new ConfiguredFeature<>(Feature.TREE, createRich().build()));

    private static TreeConfiguration.TreeConfigurationBuilder createStraightBlobTree(Block trunkBlock, Block leavesBlock, int p_195149_, int p_195150_, int p_195151_, int p_195152_) {
        return new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(trunkBlock), new StraightTrunkPlacer(p_195149_, p_195150_, p_195151_), BlockStateProvider.simple(leavesBlock), new BlobFoliagePlacer(ConstantInt.of(p_195152_), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeConfiguration.TreeConfigurationBuilder createRich() {
        return createStraightBlobTree(ModBlocks.RICH_LOG.get(), ModBlocks.RICH_LEAVES.get(), 4, 2, 0, 2).ignoreVines();
    }

    public static void register(IEventBus eventBus){
        CONFIGURED_FEATURES.register(eventBus);
    }
}
