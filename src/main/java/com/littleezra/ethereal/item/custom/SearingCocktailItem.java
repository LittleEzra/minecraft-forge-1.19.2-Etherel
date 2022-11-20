package com.littleezra.ethereal.item.custom;

import com.littleezra.ethereal.world.effect.ModMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class SearingCocktailItem extends Item {

    private static final Supplier<MobEffectInstance> EFFECT = () -> new MobEffectInstance(ModMobEffects.SIPHON.get(), 2400, 0);

    public SearingCocktailItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        Player player = entity instanceof Player ? (Player)entity : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, itemStack);
        }

        if (!level.isClientSide) {
            entity.addEffect(EFFECT.get());
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }

        if (player == null || !player.getAbilities().instabuild) {
            if (itemStack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (player != null) {
                player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        entity.gameEvent(GameEvent.DRINK);
        return itemStack;
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(getEffectDescription());
        super.appendHoverText(itemStack, level, components, flag);
    }

    public Component getEffectDescription(){
        MutableComponent mutablecomponent = Component.translatable(EFFECT.get().getDescriptionId());
        Component result = Component.literal(mutablecomponent.getString() + " (4:00)").withStyle(ChatFormatting.BLUE);

        return result;
    }
}
