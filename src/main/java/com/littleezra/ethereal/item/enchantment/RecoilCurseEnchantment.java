package com.littleezra.ethereal.item.enchantment;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

// Curse of Recoil
public class RecoilCurseEnchantment extends Enchantment {
    private final DamageSource RecoilSource = new DamageSource("recoil_curse_damage");

    protected RecoilCurseEnchantment(Rarity rarity, EnchantmentCategory category, EquipmentSlot... slots) {
        super(rarity, category, slots);
    }

    public int getMinCost(int p_45274_) {
        return 25;
    }

    public int getMaxCost(int p_45277_) {
        return 50;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.canEnchant(stack);
    }

    @Override
    public void doPostAttack(LivingEntity attacker, Entity target, int level) {
        ItemStack itemStack = attacker.getMainHandItem();
        float damage = 1f;
        if (itemStack.getItem() instanceof SwordItem sword) damage = sword.getDamage() * 0.5f;
        if (itemStack.getItem() instanceof DiggerItem digger) damage = digger.getAttackDamage() * 0.5f;
        attacker.hurt(RecoilSource, damage);
    }
}
