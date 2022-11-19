package com.littleezra.ethereal.world.effect;

import com.littleezra.ethereal.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EtherealGuardEffect extends MobEffect {
    protected EtherealGuardEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public static Map<UUID, Integer> cooldowns = new HashMap<>();

    public static final int DELAY = 240;

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {

        incrementCooldown(entity.getUUID(), -1);

        if (getCooldown(entity.getUUID()) == 0){
            entity.playSound(ModSounds.ETHEREAL_GUARD_REGEN.get());
            playBurst(entity);
        }
        if (getCooldown(entity.getUUID()) <= 0){
            playParticles(entity);
        }
    }

    public void playParticles(LivingEntity entity){
        Level level = entity.level;

        if (level instanceof ServerLevel serverLevel)
        {
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    entity.position().x,
                    entity.position().y + entity.getBbHeight() * 0.5D,
                    entity.position().z,
                    1,
                    .2d,
                    .2d,
                    .2d,
                    1d);
        }
    }

    public static void playBurst(LivingEntity entity){
        Level level = entity.level;

        if (level instanceof ServerLevel serverLevel)
        {
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    entity.position().x,
                    entity.position().y + entity.getBbHeight() * 0.5D,
                    entity.position().z,
                    30,
                    .2d,
                    .2d,
                    .2d,
                    3d);
        }
    }

    public static int getCooldown(UUID uuid){
        cooldowns.putIfAbsent(uuid, 10);
        return cooldowns.get(uuid);
    }
    public static void incrementCooldown(UUID uuid, int increment){
        cooldowns.putIfAbsent(uuid, 10);
        cooldowns.put(uuid, cooldowns.get(uuid) + increment);
    }
    public static void setCooldown(UUID uuid, int value){
        cooldowns.putIfAbsent(uuid, 10);
        cooldowns.put(uuid, value);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }
}
