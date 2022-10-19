package com.littleezra.ethereal.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab
{
    public static final CreativeModeTab ETHEREAL_TAB = new CreativeModeTab("etherealtab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ETHEREAL_SAP.get());
        }
    };
}
