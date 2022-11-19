package com.littleezra.ethereal.event;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.ModEntityTypes;
import com.littleezra.ethereal.entity.custom.*;
import com.littleezra.ethereal.player.PlayerFairyAggressor;
import com.littleezra.ethereal.player.PlayerFairyAggressorProvider;
import com.littleezra.ethereal.sound.ModSounds;
import com.littleezra.ethereal.world.effect.EtherealGuardEffect;
import com.littleezra.ethereal.world.effect.ModMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Predicate;

public class ModEvents {

    // Most events need to into ForgeEvents. Check the class of the event to see whether it is on the forge- or modbus

    @Mod.EventBusSubscriber(modid = Ethereal.MODID)
    public static class ForgeEvents
    {
        @SubscribeEvent
        public static void livingHurtEvent(LivingHurtEvent event){

            LivingEntity entity = event.getEntity();
            DamageSource source = event.getSource();
            Level level = entity.getLevel();

            // Elder Oak Curse Check

            if (entity.hasEffect(ModMobEffects.ELDER_OAK_CURSE.get()) && !entity.isDeadOrDying() && entity.getHealth() - event.getAmount() > 0){
                float amount = (float) Math.ceil(event.getAmount() * 0.5f * (entity.getEffect(ModMobEffects.ELDER_OAK_CURSE.get()).getAmplifier() + 1));

                entity.setInvulnerable(false);
                if (entity.getHealth() - amount <= 0){
                    entity.die(source);
                }
                entity.setHealth(entity.getHealth() - amount);
            }

            // Ethereal Guard Check

            if (entity.hasEffect(ModMobEffects.ETHEREAL_GUARD.get())){
                if (EtherealGuardEffect.getCooldown(entity.getUUID()) <= 0){
                    entity.sendSystemMessage(Component.literal("Protecting Entity"));

                    entity.setHealth(entity.getHealth() + event.getAmount());
                    entity.playSound(ModSounds.ETHEREAL_GUARD_BREAK.get());

                    EtherealGuardEffect.setCooldown(entity.getUUID(), EtherealGuardEffect.DELAY);
                    EtherealGuardEffect.playBurst(entity);
                }
            }
        }

        @SubscribeEvent
        public static void livingDeathEvent(LivingDeathEvent event){
            LivingEntity entity = event.getEntity();
            DamageSource source = event.getSource();
            Entity killer = source.getDirectEntity();


            if (entity instanceof Player player){
                player.getCapability(PlayerFairyAggressorProvider.PLAYER_FAIRY_AGGRESSOR).ifPresent(aggressor -> {
                    aggressor.setHurtOak(false);
                    aggressor.setHurtFairy(false);
                });
            }
            else if (entity instanceof IFairy killedFairy && killer instanceof ServerPlayer player){
                player.getCapability(PlayerFairyAggressorProvider.PLAYER_FAIRY_AGGRESSOR).ifPresent(aggressor -> {
                    aggressor.setHurtFairy(true);
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
