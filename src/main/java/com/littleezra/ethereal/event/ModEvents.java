package com.littleezra.ethereal.event;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.ModEntityTypes;
import com.littleezra.ethereal.entity.custom.*;
import com.littleezra.ethereal.particle.ModParticles;
import com.littleezra.ethereal.particle.custom.EtherealFlameParticle;
import com.littleezra.ethereal.player.PlayerFairyAggressor;
import com.littleezra.ethereal.player.PlayerFairyAggressorProvider;
import com.littleezra.ethereal.sound.ModSounds;
import com.littleezra.ethereal.world.effect.EtherealGuardEffect;
import com.littleezra.ethereal.world.effect.ModMobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

            // Ethereal Guard

            if (entity.hasEffect(ModMobEffects.ETHEREAL_GUARD.get()) && EtherealGuardEffect.getCooldown(entity.getUUID()) <= 0){
                if (event.getAmount() > EtherealGuardEffect.MIN_ABSORPTION || entity.getHealth() <= entity.getMaxHealth() * 0.5f){
                    event.setAmount(Math.max(0, event.getAmount() - EtherealGuardEffect.MAX_ABSORPTION));

                    entity.level.playSound(null,
                            entity.position().x,
                            entity.position().y,
                            entity.position().z,
                            ModSounds.ETHEREAL_GUARD_BREAK.get(), SoundSource.MASTER, 1f, 1f);

                    EtherealGuardEffect.setCooldown(
                            entity.getUUID(),
                            EtherealGuardEffect.getDelay(entity.getEffect(ModMobEffects.ETHEREAL_GUARD.get()).getAmplifier()));
                    EtherealGuardEffect.playBurst(entity);
                }
            }

            // Elder Oak Curse Check

            if (entity.hasEffect(ModMobEffects.ELDER_OAK_CURSE.get())){
                float amount = (float) Math.ceil(event.getAmount() * 0.5f * (entity.getEffect(ModMobEffects.ELDER_OAK_CURSE.get()).getAmplifier() + 1));
                event.setAmount(event.getAmount() + amount);
            }
        }

        @SubscribeEvent
        public static void livingHealEvent(LivingHealEvent event){
            LivingEntity entity = event.getEntity();

            if (entity.hasEffect(ModMobEffects.VITALITY.get())){
                float amount = (float) Math.ceil(event.getAmount() * 0.5f * (entity.getEffect(ModMobEffects.VITALITY.get()).getAmplifier() + 1));
                event.setAmount(event.getAmount() + amount);
            }
        }

        @SubscribeEvent
        public static void livingAttackEvent(LivingAttackEvent event){
            LivingEntity entity = event.getEntity();
            DamageSource source = event.getSource();
            Entity entity1 = source.getDirectEntity();

            if (entity1 instanceof LivingEntity attacker && attacker.hasEffect(ModMobEffects.SIPHON.get())){
                if (entity.getActiveEffects().stream().toList().size() > 0){
                    MobEffectInstance effect = entity.getActiveEffects().stream().toList().get(0);
                    attacker.addEffect(effect);
                    entity.removeEffect(effect.getEffect());

                    attacker.level.playSound(null,
                            entity.position().x,
                            entity.position().y,
                            entity.position().z,
                            ModSounds.SIPHON.get(), SoundSource.MASTER, 2f, 1f);
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
                    aggressor.setHurtOak(0);
                    aggressor.setHurtFairy(0);
                });
            }
            else if (entity instanceof IFairy killedFairy && killer instanceof ServerPlayer player){
                player.getCapability(PlayerFairyAggressorProvider.PLAYER_FAIRY_AGGRESSOR).ifPresent(aggressor -> {
                    aggressor.addHurtFairy(1);
                    Ethereal.FAIRY_AGGRESSOR.trigger(player);
                });
            }
        }

        @SubscribeEvent
        public static void startUsingItemEvent(LivingEntityUseItemEvent.Start event){
            LivingEntity entity = event.getEntity();
            ItemStack itemStack = event.getItem();

            if (entity.hasEffect(ModMobEffects.TACIT_MOUTH.get())){
                if (!itemStack.is(Items.MILK_BUCKET)) {
                    event.setCanceled(true);
                }
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
            Ethereal.PrintDebug("EntityAttributeCreationEvent triggered");
            event.put(ModEntityTypes.CNITHEREA.get(), Cnitherea.setAttributes());
            event.put(ModEntityTypes.TOTEM_GOLEM.get(), TotemGolem.setAttributes());
            event.put(ModEntityTypes.FAIRYFLY.get(), Fairyfly.setAttributes());
            event.put(ModEntityTypes.BUSHHOG.get(), Bushhog.setAttributes());
            event.put(ModEntityTypes.SNATCHER.get(), Snatcher.setAttributes());
            event.put(ModEntityTypes.PYRODRONE.get(), Pyrodrone.setAttributes());
            event.put(ModEntityTypes.LODER.get(), Loder.setAttributes());
            event.put(ModEntityTypes.KEEPER.get(), Keeper.setAttributes());
        }
    }
}
