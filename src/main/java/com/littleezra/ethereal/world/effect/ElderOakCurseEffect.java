package com.littleezra.ethereal.world.effect;

import com.littleezra.ethereal.entity.custom.IFairy;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.Predicate;

public class ElderOakCurseEffect extends MobEffect {
    protected ElderOakCurseEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    private int delay;

    private final int DELAY_TIME = 240;

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        delay -= 1;
        if (delay <= 0){
            delay = DELAY_TIME;
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
