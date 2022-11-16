package com.littleezra.ethereal.event;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.ModEntityTypes;
import com.littleezra.ethereal.entity.custom.*;
import com.littleezra.ethereal.player.PlayerFairyAggressor;
import com.littleezra.ethereal.player.PlayerFairyAggressorProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Predicate;

public class ModEvents {

    // Most events need to into ForgeEvents. Check the class of the event to see whether it is on the forge- or modbus

    @Mod.EventBusSubscriber(modid = Ethereal.MODID)
    public static class ForgeEvents
    {
        private static final Predicate<LivingEntity> IS_FAIRY = (entity) -> {
          return entity instanceof IFairy;
        };

        @SubscribeEvent
        public static void livingDeathEvent(LivingDeathEvent event){

            System.out.println("Living Death event fired");

            LivingEntity entity = event.getEntity();
            DamageSource source = event.getSource();
            Entity killer = source.getDirectEntity();


            if (entity instanceof Player player){
                player.getCapability(PlayerFairyAggressorProvider.PLAYER_FAIRY_AGGRESSOR).ifPresent(aggressor -> {
                    aggressor.setHurtOak(false);
                    aggressor.setHurtFairy(false);
                    player.sendSystemMessage(Component.literal("Welp, guess that's our job done."));
                });
            }
            else if (entity instanceof IFairy killedFairy && killer instanceof ServerPlayer player){
                player.getCapability(PlayerFairyAggressorProvider.PLAYER_FAIRY_AGGRESSOR).ifPresent(aggressor -> {
                    aggressor.setHurtFairy(true);
                    player.sendSystemMessage(Component.literal("Oops! You killed a fairy! NOW DIE."));
                    Ethereal.FAIRY_AGGRESSOR.trigger(player);
                });
            }
        }

        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event){
            if (event.getObject() instanceof Player){
                if (!event.getObject().getCapability(PlayerFairyAggressorProvider.PLAYER_FAIRY_AGGRESSOR).isPresent()){
                    event.addCapability(new ResourceLocation(Ethereal.MODID, "properties"), new PlayerFairyAggressorProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event){
            if (event.isWasDeath()){
                event.getOriginal().getCapability(PlayerFairyAggressorProvider.PLAYER_FAIRY_AGGRESSOR).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(PlayerFairyAggressorProvider.PLAYER_FAIRY_AGGRESSOR).ifPresent(newStore ->{
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
            event.register(PlayerFairyAggressor.class);
        }
    }

    @Mod.EventBusSubscriber(modid = Ethereal.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents
    {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event)
        {
            event.put(ModEntityTypes.CNITHEREA.get(), Cnitherea.setAttributes());
            event.put(ModEntityTypes.TOTEM_GOLEM.get(), TotemGolem.setAttributes());
            event.put(ModEntityTypes.FAIRYFLY.get(), Fairyfly.setAttributes());
            event.put(ModEntityTypes.BUSHHOG.get(), Bushhog.setAttributes());
        }


    }
}
