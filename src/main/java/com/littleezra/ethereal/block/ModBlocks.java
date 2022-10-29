package com.littleezra.ethereal.block;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.block.custom.*;
import com.littleezra.ethereal.item.ModItems;
import com.littleezra.ethereal.sound.ModSounds;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Ethereal.MODID);


    public static final RegistryObject<Block> ETHEREAL_SAP_BLOCK = registerBlock("ethereal_sap_block",
            () -> new EtherealSapBlock(BlockBehaviour.Properties.of(Material.GLASS)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion().noCollission().sound(ModSounds.ETHEREAL_SAP_BLOCK).explosionResistance(1400f)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> ETHEREAL_PLATING = registerBlock("ethereal_plating",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(1400f).sound(ModSounds.ETHEREAL_PLATING)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> CHISELED_ETHEREAL_PLATING = registerBlock("chiseled_ethereal_plating",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(1400f).sound(ModSounds.ETHEREAL_PLATING)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> ETHEREAL_BRICKS = registerBlock("ethereal_bricks",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(1400f).sound(ModSounds.ETHEREAL_PLATING)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static ToIntFunction<BlockState> lightLevel = BlockState -> 15;

    public static final RegistryObject<Block> ETHEREAL_TORCH = BLOCKS.register("ethereal_torch",
            () -> new EtherealTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).instabreak().lightLevel((p_50886_) -> {return 15; })
                    .sound(SoundType.WOOD).noCollission(), ParticleTypes.FLAME));

    public static final RegistryObject<Block> ETHEREAL_WALL_TORCH = BLOCKS.register("ethereal_wall_torch",
            () -> new EtherealWallTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).instabreak().lightLevel((p_50886_) -> {return 15; })
                    .sound(SoundType.WOOD).noCollission(), ParticleTypes.FLAME));

    public static final RegistryObject<Block> ETHEREAL_SPIKE = BLOCKS.register("ethereal_spike",
            () -> new EtherealSpikeBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(7f).requiresCorrectToolForDrops().explosionResistance(1400f).sound(ModSounds.ETHEREAL_PLATING).noCollission().noOcclusion()));

    public static final RegistryObject<Block> GHAST_SNARE = BLOCKS.register("ghast_snare",
            () -> new GhastSnareBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(7f).requiresCorrectToolForDrops().explosionResistance(1400f).sound(ModSounds.ETHEREAL_PLATING).noCollission().noOcclusion()));

    public static final RegistryObject<Block> RICH_LOG = registerBlock("rich_log", () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(3f).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RICH_WOOD = registerBlock("rich_wood", () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(3f).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RICH_PLANKS = registerBlock("rich_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(3f).sound(SoundType.WOOD)){
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }
    }, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> TREATED_PLANKS = registerBlock("treated_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(3f).sound(SoundType.WOOD)){
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }
    }, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> CHISELED_RICH_WOOD = registerBlock("chiseled_rich_wood", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(3f).sound(SoundType.WOOD)){
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }
    }, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> TRIMMED_RICH_WOOD = registerBlock("trimmed_rich_wood", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(3f).sound(SoundType.WOOD)){
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }
    }, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> SOBRIETY_TOTEM = registerBlock("sobriety_totem", () -> new TotemHeadBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(5f).sound(SoundType.WOOD)){
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }
    }, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> SURPRISE_TOTEM = registerBlock("surprise_totem", () -> new TotemHeadBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(5f).sound(SoundType.WOOD)){
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }
    }, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> SPITE_TOTEM = registerBlock("spite_totem", () -> new TotemHeadBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(5f).sound(SoundType.WOOD)){
        @Override
        public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return true;
        }

        @Override
        public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }

        @Override
        public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
            return 5;
        }
    }, CreativeModeTab.TAB_BUILDING_BLOCKS);

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab)
    {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
