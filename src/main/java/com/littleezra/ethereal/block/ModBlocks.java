package com.littleezra.ethereal.block;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.block.custom.*;
import com.littleezra.ethereal.item.ModItems;
import com.littleezra.ethereal.sound.ModSounds;
import com.littleezra.ethereal.world.feature.tree.RichPineGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
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
            () -> new SapBlock(BlockBehaviour.Properties.of(Material.GLASS)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion().noCollission().sound(ModSounds.ETHEREAL_SAP_BLOCK).explosionResistance(1400f), new Vec3(1, 1, 1)), CreativeModeTab.TAB_BUILDING_BLOCKS);

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
            () -> new SapTrapBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(7f).requiresCorrectToolForDrops().explosionResistance(1400f).sound(ModSounds.ETHEREAL_PLATING).noCollission().noOcclusion()));

    public static final RegistryObject<Block> GHAST_SNARE = BLOCKS.register("ghast_snare",
            () -> new GhastSnareBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(7f).requiresCorrectToolForDrops().explosionResistance(1400f).sound(ModSounds.ETHEREAL_PLATING).noCollission().noOcclusion()));

    // NECROTIC SAP

    public static final RegistryObject<Block> NECROTIC_SAP_BLOCK = registerBlock("necrotic_sap_block",
            () -> new NecroticSapBlock(BlockBehaviour.Properties.of(Material.GLASS)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion().noCollission().sound(SoundType.WART_BLOCK).explosionResistance(500f), new Vec3(0.5D, 0.5D, 0.5D)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> SHARPSHOOTER = registerBlock("sharpshooter",
            () -> new SharpshooterBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(7f).requiresCorrectToolForDrops().explosionResistance(1400f).noOcclusion()), CreativeModeTab.TAB_REDSTONE);

    // VERDANT SAP

    public static final RegistryObject<Block> VERDANT_SAP_BLOCK = registerBlock("verdant_sap_block",
            () -> new SapBlock(BlockBehaviour.Properties.of(Material.GLASS)
                    .strength(4f).requiresCorrectToolForDrops().noOcclusion().noCollission().sound(SoundType.HONEY_BLOCK), new Vec3(0.25D, 0.25D, 0.25D)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> RICH_SAPLING = registerBlock("rich_sapling", () ->
            new SaplingBlock(new RichPineGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), CreativeModeTab.TAB_DECORATIONS);

    public static final RegistryObject<Block> RICH_LEAVES = registerBlock("rich_leaves", () -> leaves(SoundType.GRASS), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> RICH_LOG = registerBlock("rich_log", () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RICH_WOOD = registerBlock("rich_wood", () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(2f).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> STRIPPED_RICH_LOG = registerBlock("stripped_rich_log", () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> STRIPPED_RICH_WOOD = registerBlock("stripped_rich_wood", () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(2f).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> RICH_PLANKS = registerBlock("rich_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RICH_STAIRS = registerBlock("rich_stairs", () -> new StairBlock(() -> RICH_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(RICH_PLANKS.get())
            .strength(1f)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RICH_SLAB = registerBlock("rich_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(RICH_PLANKS.get())){
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
            .strength(1f).sound(SoundType.WOOD)){
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
    public static final RegistryObject<Block> TREATED_STAIRS = registerBlock("treated_stairs", () -> new StairBlock(() -> TREATED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(TREATED_PLANKS.get())
            .strength(1f)){
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
    public static final RegistryObject<Block> TREATED_SLAB = registerBlock("treated_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(TREATED_PLANKS.get())){
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
            .strength(1f).sound(SoundType.WOOD)){
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
            .strength(1f).sound(SoundType.WOOD)){
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
            .strength(2f).sound(SoundType.WOOD)){
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
            .strength(2f).sound(SoundType.WOOD)){
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
            .strength(2f).sound(SoundType.WOOD)){
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

    public static final RegistryObject<Block> RICH_FENCE = registerBlock("rich_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(RICH_PLANKS.get())){
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
    public static final RegistryObject<Block> RICH_FENCE_GATE = registerBlock("rich_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(RICH_PLANKS.get())){
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

    public static final RegistryObject<Block> TREATED_FENCE = registerBlock("treated_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(TREATED_PLANKS.get())){
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
    public static final RegistryObject<Block> TREATED_FENCE_GATE = registerBlock("treated_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(TREATED_PLANKS.get())){
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

    public static final RegistryObject<Block> RICH_DOOR = registerBlock("rich_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(RICH_PLANKS.get()).noOcclusion()), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block> RICH_TRAPDOOR = registerBlock("rich_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(RICH_PLANKS.get()).noOcclusion()), CreativeModeTab.TAB_REDSTONE);

    public static final RegistryObject<Block> TREATED_DOOR = registerBlock("treated_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(TREATED_PLANKS.get()).noOcclusion()), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block> TREATED_TRAPDOOR = registerBlock("treated_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(TREATED_PLANKS.get()).noOcclusion()), CreativeModeTab.TAB_REDSTONE);

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }
    private static LeavesBlock leaves(SoundType p_152615_) {
        return new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(p_152615_).noOcclusion().isValidSpawn(ModBlocks::ocelotOrParrot).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never));
    }
    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }
    private static Boolean ocelotOrParrot(BlockState p_50822_, BlockGetter p_50823_, BlockPos p_50824_, EntityType<?> p_50825_) {
        return (boolean)(p_50825_ == EntityType.OCELOT || p_50825_ == EntityType.PARROT);
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
