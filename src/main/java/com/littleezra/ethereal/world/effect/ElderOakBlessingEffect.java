package com.littleezra.ethereal.world.effect;

import com.littleezra.ethereal.entity.custom.IFairy;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ElderOakBlessingEffect extends MobEffect {
    private static final DamageSource ELDER_OAK_CURSE = new DamageSource("elder_oak_curse");
    private static final DamageSource ELDER_OAK_BLESSING = new DamageSource("elder_oak_blessing");

    protected ElderOakBlessingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    private final Predicate<LivingEntity> nonFairyHostile = (entity) -> {

        boolean flag = false;

        if (!(entity instanceof IFairy)){
            flag = !entity.getType().getCategory().isFriendly();
        }

        return flag;
    };

    int delay;
    static final int DELAY = 60;
    static final float FORCE = 1;

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier)
    {
        delay -= 1;

        if (entity instanceof Player player){
            if (IFairy.playerIsAggressor(player))
            {
                int duration = entity.getEffect(ModMobEffects.ELDER_OAK_BLESSING.get()).getDuration();

                entity.removeEffect(ModMobEffects.ELDER_OAK_BLESSING.get());
                entity.addEffect(new MobEffectInstance(ModMobEffects.ELDER_OAK_CURSE.get(), duration));
            }
        }
        if (delay <= 0){
            List<Entity> entities = entity.level.getEntities(entity, entity.getBoundingBox().inflate(4));
            for (Entity entity1 : entities) {
                if (entity1 instanceof LivingEntity livingEntity && nonFairyHostile.test(livingEntity))
                {
                    livingEntity.hurt(ELDER_OAK_BLESSING, 2f);
                }
            }
            delay = DELAY;
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
