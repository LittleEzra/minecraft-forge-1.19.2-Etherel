package com.littleezra.etherel.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab
{
    public static final CreativeModeTab ETHEREL_TAB = new CreativeModeTab("ethereltab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ETHEREL_SAP.get());
        }
    };
}
