package com.littleezra.ethereal;

import com.littleezra.ethereal.block.ModBlocks;
import com.littleezra.ethereal.entity.ModEntityTypes;
import com.littleezra.ethereal.entity.client.CnithereaRenderer;
import com.littleezra.ethereal.item.ModItems;
import com.littleezra.ethereal.sound.ModSounds;
import com.littleezra.ethereal.world.feature.ModConfiguredFeatures;
import com.littleezra.ethereal.world.feature.ModPlacedFeatures;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Ethereal.MODID)
public class Ethereal
{
    public static final String MODID = "ethereal";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Ethereal()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModEntityTypes.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntityTypes.CNITHEREA.get(), CnithereaRenderer::new);
        }
    }
}
