package com.littleezra.ethereal.world.effect;

import com.littleezra.ethereal.entity.custom.IFairy;
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
}
