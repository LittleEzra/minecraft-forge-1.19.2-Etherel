package com.littleezra.ethereal;

import com.littleezra.ethereal.advancements.critereon.FairyAggressorTrigger;
import com.littleezra.ethereal.block.ModBlocks;
import com.littleezra.ethereal.block.entity.ModBlockEntities;
import com.littleezra.ethereal.entity.ModEntityTypes;
import com.littleezra.ethereal.entity.client.BushhogRenderer;
import com.littleezra.ethereal.entity.client.CnithereaRenderer;
import com.littleezra.ethereal.entity.client.FairyflyRenderer;
import com.littleezra.ethereal.entity.client.TotemGolemRenderer;
import com.littleezra.ethereal.item.ModItems;
import com.littleezra.ethereal.loot.ModLootModifiers;
import com.littleezra.ethereal.networking.ModMessages;
import com.littleezra.ethereal.particle.ModParticles;
import com.littleezra.ethereal.screen.ModMenuTypes;
import com.littleezra.ethereal.screen.SharpshooterMenu;
import com.littleezra.ethereal.screen.SharpshooterScreen;
import com.littleezra.ethereal.sound.ModSounds;
import com.littleezra.ethereal.world.feature.ModConfiguredFeatures;
import com.littleezra.ethereal.world.feature.ModPlacedFeatures;
import com.mojang.logging.LogUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Ethereal.MODID)
public class Ethereal
{
    public static FairyAggressorTrigger FAIRY_AGGRESSOR = null;

    public static final String MODID = "ethereal";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static List<Player> hurtElderOak = List.of();

    public Ethereal()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModSounds.register(modEventBus);
        ModParticles.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModEntityTypes.register(modEventBus);

        ModMenuTypes.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->{
            ModMessages.register();
            FAIRY_AGGRESSOR = CriteriaTriggers.register(new FairyAggressorTrigger());
        });
    }

    public static ServerPlayer getServerPlayer(Player player){
        return player.getServer().getPlayerList().getPlayer(player.getUUID());
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntityTypes.CNITHEREA.get(), CnithereaRenderer::new);
            EntityRenderers.register(ModEntityTypes.TOTEM_GOLEM.get(), TotemGolemRenderer::new);
            EntityRenderers.register(ModEntityTypes.FAIRYFLY.get(), FairyflyRenderer::new);
            EntityRenderers.register(ModEntityTypes.BUSHHOG.get(), BushhogRenderer::new);

            MenuScreens.register(ModMenuTypes.SHARPSHOOTER_MENU.get(), SharpshooterScreen::new);
        }
    }
}
