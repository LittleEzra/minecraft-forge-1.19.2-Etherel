package com.littleezra.ethereal.item;

import com.littleezra.ethereal.Ethereal;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
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
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));
    public static final RegistryObject<Item> ETHEREAL_CONDENSATE = ITEMS.register("ethereal_condensate",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
