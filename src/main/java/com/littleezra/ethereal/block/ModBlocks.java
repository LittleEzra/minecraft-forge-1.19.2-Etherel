package com.littleezra.ethereal.block;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.block.custom.*;
import com.littleezra.ethereal.item.ModItems;
import com.littleezra.ethereal.particle.ModParticles;
import com.littleezra.ethereal.sound.ModSounds;
import com.littleezra.ethereal.world.feature.tree.RichTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
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

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Ethereal.MODID);

    private static final Supplier<ParticleOptions> ETHEREAL_FLAME_PARTICLES = () -> ModParticles.ETHEREAL_FLAME.get();

    // ETHEREAL SAP

    public static final RegistryObject<Block> ETHEREAL_SAP_BLOCK = registerBlock("ethereal_sap_block",
            () -> new SapBlock(BlockBehaviour.Properties.of(Material.GLASS)
                    .strength(3f).requiresCorrectToolForDrops().noOcclusion().noCollission().sound(ModSounds.ETHEREAL_SAP_BLOCK).explosionResistance(1400f), new Vec3(1, 1, 1)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> ETHEREAL_PLATING = registerBlock("ethereal_plating",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(1400f).sound(ModSounds.ETHEREAL_PLATING)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> CHISELED_ETHEREAL_PLATING = registerBlock("chiseled_ethereal_plating",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(1400f).sound(ModSounds.ETHEREAL_PLATING)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> ETHEREAL_BRICKS = registerBlock("ethereal_bricks",
            () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(5f).requiresCorrectToolForDrops().explosionResistance(1400f).sound(ModSounds.ETHEREAL_PLATING)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> ETHEREAL_TORCH = BLOCKS.register("ethereal_torch",
            () -> new EtherealTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).instabreak().lightLevel((state) -> {return 15; })
                    .sound(SoundType.WOOD).noCollission(), ETHEREAL_FLAME_PARTICLES));

    public static final RegistryObject<Block> ETHEREAL_WALL_TORCH = BLOCKS.register("ethereal_wall_torch",
            () -> new EtherealWallTorchBlock(BlockBehaviour.Properties.of(Material.DECORATION).instabreak().lightLevel((state) -> {return 15; })
                    .sound(SoundType.WOOD).noCollission(), ETHEREAL_FLAME_PARTICLES));

    public static final RegistryObject<Block> SAP_TRAP = BLOCKS.register("sap_trap",
            () -> new SapTrapBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(7f).requiresCorrectToolForDrops().explosionResistance(1400f).sound(ModSounds.ETHEREAL_PLATING).noCollission().noOcclusion()));

    public static final RegistryObject<Block> GHAST_SNARE = BLOCKS.register("ghast_snare",
            () -> new GhastSnareBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(7f).requiresCorrectToolForDrops().explosionResistance(1400f).sound(ModSounds.ETHEREAL_PLATING).noCollission().noOcclusion()));

    // NECROTIC SAP

    public static final RegistryObject<Block> NECROTIC_SAP_BLOCK = registerBlock("necrotic_sap_block",
            () -> new NecroticSapBlock(BlockBehaviour.Properties.of(Material.GLASS)
                    .strength(3f).requiresCorrectToolForDrops().noOcclusion().noCollission().sound(SoundType.WART_BLOCK).explosionResistance(500f), new Vec3(0.5D, 0.5D, 0.5D)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> SHARPSHOOTER = registerBlock("sharpshooter",
            () -> new SharpshooterBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)
                    .strength(7f).requiresCorrectToolForDrops().explosionResistance(1400f).noOcclusion()), CreativeModeTab.TAB_REDSTONE);

    // VERDANT SAP

    public static final RegistryObject<Block> VERDANT_SAP_BLOCK = registerBlock("verdant_sap_block",
            () -> new SapBlock(BlockBehaviour.Properties.of(Material.GLASS)
                    .strength(3f).requiresCorrectToolForDrops().noOcclusion().noCollission().sound(SoundType.HONEY_BLOCK), new Vec3(0.25D, 0.25D, 0.25D)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> AMBER_BLOCK = registerBlock("amber_block",
            () -> new AmberBlock(BlockBehaviour.Properties.of(Material.GLASS)
                    .strength(2f).noOcclusion().sound(SoundType.STONE)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> SCULK_HUSK = registerBlock("sculk_husk", () -> new SculkHuskBlock(BlockBehaviour.Properties.of(Material.SCULK)
            .strength(0.5f).sound(SoundType.SCULK)),
            CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> ELDER_VINE = registerBlock("elder_vine", () -> new ElderVineBlock(BlockBehaviour.Properties.of(Material.PLANT)
            .randomTicks().noCollission()
            .instabreak().sound(SoundType.GRASS)),
            CreativeModeTab.TAB_DECORATIONS
    );
    public static final RegistryObject<Block> ELDER_VINE_PLANT = BLOCKS.register("elder_vine_plant", () -> new ElderVinePlantBlock(BlockBehaviour.Properties.of(Material.PLANT)
            .randomTicks().noCollission()
            .instabreak().sound(SoundType.GRASS)
    ));

    public static final RegistryObject<Block> RICH_SAPLING = registerBlock("rich_sapling", () ->
            new SaplingBlock(new RichTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)), CreativeModeTab.TAB_DECORATIONS);

    public static final RegistryObject<Block> RICH_LOG = registerBlock("rich_log", () -> new RichLogBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(2f).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RICH_WOOD = registerBlock("rich_wood", () -> new RichLogBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(2f).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> STRIPPED_RICH_LOG = registerBlock("stripped_rich_log", () -> new RichLogBlock(BlockBehaviour.Properties.copy(RICH_LOG.get())
            .sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> STRIPPED_RICH_WOOD = registerBlock("stripped_rich_wood", () -> new RichLogBlock(BlockBehaviour.Properties.copy(RICH_WOOD.get())
            .sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> ELDER_WOOD = registerBlock("elder_wood", () -> new ElderWoodBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(10f).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> RICH_PLANKS = registerBlock("rich_planks", () -> simpleFlammableBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(1f).sound(SoundType.WOOD), 5, 5), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RICH_STAIRS = registerBlock("rich_stairs", () -> flammableStairBlock(RICH_PLANKS.get(), BlockBehaviour.Properties.copy(RICH_PLANKS.get()), 5, 5), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RICH_SLAB = registerBlock("rich_slab", () -> flammableSlabBlock(BlockBehaviour.Properties.copy(RICH_PLANKS.get()), 5, 5), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> TREATED_PLANKS = registerBlock("treated_planks", () -> simpleFlammableBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(1f).sound(SoundType.WOOD), 5, 5), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> TREATED_STAIRS = registerBlock("treated_stairs", () -> flammableStairBlock(TREATED_PLANKS.get(), BlockBehaviour.Properties.copy(TREATED_PLANKS.get())
            .strength(1f), 5, 5), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> TREATED_SLAB = registerBlock("treated_slab", () -> flammableSlabBlock(BlockBehaviour.Properties.copy(TREATED_PLANKS.get()), 5, 5), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> CHISELED_RICH_WOOD = registerBlock("chiseled_rich_wood", () -> simpleFlammableBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(1f).sound(SoundType.WOOD), 5, 5), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> TRIMMED_RICH_WOOD = registerBlock("trimmed_rich_wood", () -> simpleFlammableBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(1f).sound(SoundType.WOOD), 5, 5), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> SOBRIETY_TOTEM = registerBlock("sobriety_totem", () -> new TotemHeadBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(2f).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> SURPRISE_TOTEM = registerBlock("surprise_totem", () -> new TotemHeadBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(2f).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> SPITE_TOTEM = registerBlock("spite_totem", () -> new TotemHeadBlock(BlockBehaviour.Properties.of(Material.WOOD)
            .strength(2f).sound(SoundType.WOOD)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> RICH_LEAVES = registerBlock("rich_leaves", () -> leaves(SoundType.GRASS), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> RICH_FENCE = registerBlock("rich_fence", () -> flammableFenceBlock(BlockBehaviour.Properties.copy(RICH_PLANKS.get()), 5, 5), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> RICH_FENCE_GATE = registerBlock("rich_fence_gate", () -> flammableFenceGateBlock(BlockBehaviour.Properties.copy(RICH_PLANKS.get()), 5, 5), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> TREATED_FENCE = registerBlock("treated_fence", () -> flammableFenceBlock(BlockBehaviour.Properties.copy(TREATED_PLANKS.get()), 5, 5), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> TREATED_FENCE_GATE = registerBlock("treated_fence_gate", () -> flammableFenceGateBlock(BlockBehaviour.Properties.copy(TREATED_PLANKS.get()), 5, 5), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> RICH_DOOR = registerBlock("rich_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(RICH_PLANKS.get()).noOcclusion()), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block> RICH_TRAPDOOR = registerBlock("rich_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(RICH_PLANKS.get()).noOcclusion()), CreativeModeTab.TAB_REDSTONE);

    public static final RegistryObject<Block> TREATED_DOOR = registerBlock("treated_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(TREATED_PLANKS.get()).noOcclusion()), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block> TREATED_TRAPDOOR = registerBlock("treated_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(TREATED_PLANKS.get()).noOcclusion()), CreativeModeTab.TAB_REDSTONE);

    public static final RegistryObject<Block> RICH_BUTTON = registerBlock("rich_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Block> TREATED_BUTTON = registerBlock("treated_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)), CreativeModeTab.TAB_REDSTONE);

    public static final RegistryObject<Block> SHEEN_BLOOM = registerBlock("sheen_bloom", () -> new SheenBloomBlock(BlockBehaviour.Properties.copy(Blocks.GRASS).lightLevel((state) -> 4).offsetType(BlockBehaviour.OffsetType.XZ)), CreativeModeTab.TAB_DECORATIONS);

    public static final RegistryObject<Block> GILDBLOSSOM = registerBlock("gildblossom",
            () -> new FlowerBlock(MobEffects.GLOWING, 4, BlockBehaviour.Properties.copy(Blocks.POPPY)), CreativeModeTab.TAB_DECORATIONS);

    public static final RegistryObject<Block> POTTED_GILDBLOSSOM = BLOCKS.register("potted_gildblossom",
            () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.GILDBLOSSOM, BlockBehaviour.Properties.copy(Blocks.POTTED_POPPY)));

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

    private static Block simpleFlammableBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new Block(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static SlabBlock flammableSlabBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new SlabBlock(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static StairBlock flammableStairBlock(Block base, BlockBehaviour.Properties properties, int flammability, int firespread){
        return new StairBlock(() -> base.defaultBlockState(), properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static FenceBlock flammableFenceBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new FenceBlock(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static FenceGateBlock flammableFenceGateBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new FenceGateBlock(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
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
