package com.littleezra.ethereal.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TransmuteMobEffect extends MobEffect {
    protected TransmuteMobEffect(MobEffectCategory category, int color) {
        super(category, color); // Use color 3203440
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int p_19468_) {
        super.applyEffectTick(entity, p_19468_);
    }

    @SubscribeEvent
    public static void onBlockBreakEvent(BlockEvent.BreakEvent event){

    }
}
