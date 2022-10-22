package com.littleezra.ethereal.item;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.block.ModBlocks;
import com.littleezra.ethereal.item.custom.AetherMusicDiscItem;
import com.littleezra.ethereal.item.custom.EtherealConcentrateItem;
import com.littleezra.ethereal.sound.ModSounds;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Ethereal.MODID);


    public static final RegistryObject<Item> ETHEREAL_SAP = ITEMS.register("ethereal_sap",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> MUSIC_DISC_AETHER = ITEMS.register("music_disc_aether",
            () -> new AetherMusicDiscItem(0, () -> ModSounds.MUSIC_DISC_AETHER.get(),
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC).rarity(Rarity.RARE).stacksTo(1), 760));

    public static final RegistryObject<Item> ETHEREAL_CONCENTRATE = ITEMS.register("ethereal_concentrate",
            () -> new EtherealConcentrateItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).food(
                    new FoodProperties.Builder().nutrition(4).saturationMod(10).alwaysEat().build())));

    public static final RegistryObject<Item> ETHEREAL_TORCH = ITEMS.register("ethereal_torch",
            () -> new StandingAndWallBlockItem(ModBlocks.ETHEREAL_TORCH.get(), ModBlocks.ETHEREAL_WALL_TORCH.get(), (new Item.Properties()).tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final RegistryObject<Item> ETHEREAL_SPIKE = ITEMS.register("ethereal_spike",
            () -> new BlockItem(ModBlocks.ETHEREAL_SPIKE.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));

    public static final RegistryObject<Item> GHAST_SNARE = ITEMS.register("ghast_snare",
            () -> new BlockItem(ModBlocks.GHAST_SNARE.get(), new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE)));


    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
