package com.littleezra.ethereal;

import com.littleezra.ethereal.advancements.critereon.CnithereaBounceTrigger;
import com.littleezra.ethereal.advancements.critereon.FairyAggressorTrigger;
import com.littleezra.ethereal.block.ModBlocks;
import com.littleezra.ethereal.block.entity.ModBlockEntities;
import com.littleezra.ethereal.entity.ModEntityTypes;
import com.littleezra.ethereal.entity.client.*;
import com.littleezra.ethereal.entity.custom.Cnitherea;
import com.littleezra.ethereal.item.ModItems;
import com.littleezra.ethereal.item.enchantment.ModEnchantments;
import com.littleezra.ethereal.loot.ModLootModifiers;
import com.littleezra.ethereal.networking.ModMessages;
import com.littleezra.ethereal.particle.ModParticles;
import com.littleezra.ethereal.screen.ModMenuTypes;
import com.littleezra.ethereal.screen.SharpshooterScreen;
import com.littleezra.ethereal.sound.ModSounds;
import com.littleezra.ethereal.world.effect.ModMobEffects;
import com.littleezra.ethereal.world.feature.ModConfiguredFeatures;
import com.littleezra.ethereal.world.feature.ModFeatures;
import com.littleezra.ethereal.world.feature.ModPlacedFeatures;
import com.mojang.logging.LogUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.levelgen.Heightmap;
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
    public static FairyAggressorTrigger  FAIRY_AGGRESSOR = null;
    public static CnithereaBounceTrigger CNITHEREA_BOUNCE = null;

    public static final String MODID = "ethereal";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Ethereal()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModParticles.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModSounds.register(modEventBus);

        ModFeatures.register(modEventBus);

        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);

        ModEntityTypes.register(modEventBus);

        ModMenuTypes.register(modEventBus);
        ModLootModifiers.register(modEventBus);

        ModMobEffects.register(modEventBus);
        ModEnchantments.register(modEventBus);

        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->{
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.GILDBLOSSOM.getId(), ModBlocks.POTTED_GILDBLOSSOM);
            SpawnPlacements.register(ModEntityTypes.CNITHEREA.get(), SpawnPlacements.Type.NO_RESTRICTIONS,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Cnitherea::canSpawn);

            ModMessages.register();
            FAIRY_AGGRESSOR = CriteriaTriggers.register(new FairyAggressorTrigger());
            CNITHEREA_BOUNCE = CriteriaTriggers.register(new CnithereaBounceTrigger());
        });
    }

    public static ServerPlayer getServerPlayer(Player player){
        if (player.getServer() != null)
            return player.getServer().getPlayerList().getPlayer(player.getUUID());
        return null;
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
            EntityRenderers.register(ModEntityTypes.SNATCHER.get(), SnatcherRenderer::new);
            EntityRenderers.register(ModEntityTypes.PYRODRONE.get(), PyrodroneRenderer::new);
            EntityRenderers.register(ModEntityTypes.LODER.get(), LoderRenderer::new);
            EntityRenderers.register(ModEntityTypes.KEEPER.get(), KeeperRenderer::new);
;
            MenuScreens.register(ModMenuTypes.SHARPSHOOTER_MENU.get(), SharpshooterScreen::new);
        }
    }
}
