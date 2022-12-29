package com.littleezra.ethereal.item.custom;

import com.littleezra.ethereal.entity.custom.IFairy;
import com.littleezra.ethereal.player.PlayerFairyAggressor;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class AmbrosiaItem extends BowlFoodItem {
    public AmbrosiaItem(Properties properties) {
        super(properties);
    }

    // return this.isEdible() ? entity.eat(level, itemStack) : itemStack;

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        Player player = entity instanceof Player ? (Player)entity : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, itemStack);
        }

        if (!level.isClientSide && player != null) {
            PlayerFairyAggressor aggressor = IFairy.playerGetAggressor(player);
            if (aggressor != null){
                if (aggressor.getHurtFairy() > 0) aggressor.subHurtFairy(1);
                if (aggressor.getHurtOak() > 0) aggressor.subHurtOak(1);
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }

        if (player == null || !player.getAbilities().instabuild) {
            if (itemStack.isEmpty()) {
                return new ItemStack(Items.BOWL);
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(Items.BOWL));
            }
        }

        entity.gameEvent(GameEvent.DRINK);
        return itemStack;
    }
}
