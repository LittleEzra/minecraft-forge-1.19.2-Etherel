package com.littleezra.ethereal.event;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.ModEntityTypes;
import com.littleezra.ethereal.entity.custom.Cnitherea;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {

    // !IMPORTANT! put events into the ForgeEvents class.

    @Mod.EventBusSubscriber(modid = Ethereal.MODID)
    public static class ForgeEvents
    {

    }

    @Mod.EventBusSubscriber(modid = Ethereal.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents
    {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event)
        {
            event.put(ModEntityTypes.CNITHEREA.get(), Cnitherea.setAttributes());
        }
    }
}
