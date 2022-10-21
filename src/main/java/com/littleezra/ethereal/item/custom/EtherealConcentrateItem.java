package com.littleezra.ethereal.item.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EtherealConcentrateItem extends Item {

    public EtherealConcentrateItem(Properties p_41383_) {
        super(p_41383_);
    }

    private static final MobEffectInstance mobEffectInstance = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1);

    @Override
    public boolean isEdible() {
        return true;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity)
    {
        Player player = livingEntity instanceof Player ? (Player)livingEntity : null;
        if (!level.isClientSide) {
            if (mobEffectInstance.getEffect().isInstantenous()) {
                mobEffectInstance.getEffect().applyInstantenousEffect(player, player, livingEntity, mobEffectInstance.getAmplifier(), 1.0D);
            } else {
                livingEntity.addEffect(new MobEffectInstance(mobEffectInstance));
            }
        }
        if (player != null) {
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }
        return itemStack;
    }
}
