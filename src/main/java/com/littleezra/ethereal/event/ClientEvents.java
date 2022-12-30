package com.littleezra.ethereal.event;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.particle.ModParticles;
import com.littleezra.ethereal.particle.custom.EtherealFlameParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = Ethereal.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents{

    }

    @Mod.EventBusSubscriber(modid = Ethereal.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents{
        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event){
            Ethereal.PrintDebug("RegisterParticleProvidersEvent triggered");
            event.register(ModParticles.ETHEREAL_FLAME.get(), EtherealFlameParticle.Provider::new);
        }
    }
}
