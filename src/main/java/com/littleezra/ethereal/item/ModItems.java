package com.littleezra.ethereal.item;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.block.ModBlocks;
import com.littleezra.ethereal.entity.ModEntityTypes;
import com.littleezra.ethereal.item.custom.*;
import com.littleezra.ethereal.sound.ModSounds;
import com.littleezra.ethereal.world.effect.ModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems
{
    private static final Supplier<MobEffectInstance> etherealEffect = () -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 1);
    private static final Supplier<MobEffectInstance> necroticEffect = () -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 2);
    private static final Supplier<MobEffectInstance> verdantEffect  = () -> new MobEffectInstance(MobEffects.ABSORPTION, 500, 2);
    private static final Supplier<MobEffectInstance> SEARING_COCKTAIL_EFFECT = () -> new MobEffectInstance(ModMobEffects.LAVA_WALKING.get(), 2400, 0);
    private static final Supplier<MobEffectInstance> HARDY_BREW_EFFECT = () -> new MobEffectInstance(ModMobEffects.ELDER_OAK_BLESSING.get(), 2400, 0);

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Ethereal.MODID);


    public static final RegistryObject<Item> ETHEREAL_SAP = ITEMS.register("ethereal_sap",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> MUSIC_DISC_AETHER = ITEMS.register("music_disc_aether",
            () -> new ModRecordItem(0, ModSounds.MUSIC_DISC_AETHER::get,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC).rarity(Rarity.RARE).stacksTo(1), 1840, "LittleEzra - Aether"));

    public static final RegistryObject<Item> ETHEREAL_CONCENTRATE = ITEMS.register("ethereal_concentrate",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).food(
                    new FoodProperties.Builder().effect(etherealEffect, 1f).alwaysEat().build())));

    public static final RegistryObject<Item> ETHEREAL_TORCH = ITEMS.register("ethereal_torch",
            () -> new StandingAndWallBlockItem(ModBlocks.ETHEREAL_TORCH.get(), ModBlocks.ETHEREAL_WALL_TORCH.get(), (new Item.Properties()).tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<Item> ETHEREAL_SPIKE = ITEMS.register("ethereal_spike",
            () -> new BlockItem(ModBlocks.ETHEREAL_SPIKE.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));

    public static final RegistryObject<Item> GHAST_SNARE = ITEMS.register("ghast_snare",
            () -> new BlockItem(ModBlocks.GHAST_SNARE.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));

    public static final RegistryObject<Item> CNITHEREA_SPAWN_EGG = ITEMS.register("cnitherea_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.CNITHEREA, 0x6495d0, 0xa3e1ff, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> TOTEM_GOLEM_SPAWN_EGG = ITEMS.register("totem_golem_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.TOTEM_GOLEM, 0x60281a, 0x804b2c, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> FAIRYFLY_SPAWN_EGG = ITEMS.register("fairyfly_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.FAIRYFLY, 0xc3a9db, 0xffd695, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BUSHHOG_SPAWN_EGG = ITEMS.register("bushhog_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.BUSHHOG, 0x6c8031, 0x9c8067, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    // NECROTIC SAP

    public static final RegistryObject<Item> NECROTIC_SAP = ITEMS.register("necrotic_sap",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> NECROTIC_CONCENTRATE = ITEMS.register("necrotic_concentrate",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).food(
                    new FoodProperties.Builder().effect(necroticEffect, 1f).alwaysEat().build())));

    public static final RegistryObject<Item> SEARING_COCKTAIL = ITEMS.register("searing_cocktail",
            () -> new ModPotionItem(new Item.Properties().tab(CreativeModeTab.TAB_BREWING), SEARING_COCKTAIL_EFFECT));

    // VERDANT SAP

    public static final RegistryObject<Item> VERDANT_SAP = ITEMS.register("verdant_sap",
            () -> new VerdantSapItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> VERDANT_CONCENTRATE = ITEMS.register("verdant_concentrate",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).food(
                    new FoodProperties.Builder().effect(verdantEffect, 1f).alwaysEat().build())));

    public static final RegistryObject<Item> SCULK_HEART = ITEMS.register("sculk_heart",
            () -> new VerdantSapItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));

    public static final RegistryObject<Item> HARDY_BREW = ITEMS.register("hardy_brew",
            () -> new ModPotionItem(new Item.Properties().tab(CreativeModeTab.TAB_BREWING), HARDY_BREW_EFFECT));

    // MISC

    public static final RegistryObject<Item> INSTA_VITA = ITEMS.register("insta_vita",
            () -> new InstaVitaItem(new Item.Properties()));


    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
