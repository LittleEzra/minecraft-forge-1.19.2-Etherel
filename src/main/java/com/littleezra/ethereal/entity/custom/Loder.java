package com.littleezra.ethereal.entity.custom;

import io.netty.util.AttributeMap;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Loder extends PathfinderMob implements IAnimatable {
    public AnimationFactory factory = new AnimationFactory(this);

    private boolean attacking;

    public boolean isAttacking(){ return attacking; }
    public void setAttacking(boolean value){ attacking = value; }

    public Loder(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 2D)
                .add(Attributes.FOLLOW_RANGE, 10D).build();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    public PlayState predicate(AnimationEvent event){
        if (attacking){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("loder.animation.drill"));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("loder.animation.idle"));

        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LoderAttackGoal(this, .5D, 2, 4, 0f));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, .7D));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (entity) -> {
            return Math.abs(entity.getY() - this.getY()) <= 4.0D;
        }));
    }

    @Override
    public void tick() {
        super.tick();
        if (isEffectiveAi()){
            List<Player> players = this.level.getNearbyPlayers(TargetingConditions.forCombat(), this, getBoundingBox().inflate(1D));
            if (players.size() > 0){
                players.forEach(player -> {
                    player.hurt(DamageSource.mobAttack(this), 2f);
                    this.setAttacking(true);
                });
            } else{
                this.setAttacking(false);
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.level.broadcastEntityEvent(this, (byte)4);
        float damage = 2f;
        float f1 = (int)damage > 0 ? damage / 2.0F + (float)this.random.nextInt((int)damage) : damage;
        boolean flag = entity.hurt(DamageSource.mobAttack(this), f1);
        if (flag) {
            double d2;
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                d2 = livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            } else {
                d2 = 0.0D;
            }

            double d0 = d2;
            double d1 = Math.max(0.0D, 1.0D - d0);
            entity.setDeltaMovement(entity.getDeltaMovement().add(0.0D, (double)0.4F * d1, 0.0D));
            this.doEnchantDamageEffects(this, entity);
        }

        return flag;
    }

    public boolean isMoving(){
        return getDeltaMovement().length() > 0D;
    }

    public boolean isMoving(double min){
        return getDeltaMovement().length() > min;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    static class LoderAttackGoal extends Goal{
        private Loder loder;
        @Nullable
        private LivingEntity target;
        private int attackTime = -1;
        private final double speedModifier;
        private int seeTime;
        private final int attackIntervalMin;
        private final int attackIntervalMax;
        private final float attackRadius;
        private final float attackRadiusSqr;

        public LoderAttackGoal(Loder loder, double speedModifier, int attackIntervalMin, int attackIntervalMax, float attackRadius){
            this.loder = loder;
            this.speedModifier = speedModifier;
            this.attackIntervalMin = attackIntervalMin;
            this.attackIntervalMax = attackIntervalMax;
            this.attackRadius = attackRadius;
            this.attackRadiusSqr = attackRadius * attackRadius;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public void tick() {
            double d0 = this.loder.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
            boolean flag = this.loder.getSensing().hasLineOfSight(this.target);
            if (flag) {
                ++this.seeTime;
            } else {
                this.seeTime = 0;
            }

            if (!(d0 > (double)this.attackRadiusSqr) && this.seeTime >= 5) {
                this.loder.getNavigation().stop();
            } else {
                this.loder.getNavigation().moveTo(this.target, this.speedModifier);
            }

            this.loder.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
            if (--this.attackTime == 0) {
                if (!flag) {
                    return;
                }

                float f = (float)Math.sqrt(d0) / this.attackRadius;
                float f1 = Mth.clamp(f, 0.1F, 1.0F);
                this.attackTime = Mth.floor(f * (float)(this.attackIntervalMax - this.attackIntervalMin) + (float)this.attackIntervalMin);
            } else if (this.attackTime < 0) {
                this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double)this.attackRadius, (double)this.attackIntervalMin, (double)this.attackIntervalMax));
            }
        }

        @Override
        public boolean canUse() {
            LivingEntity livingentity = this.loder.getTarget();
            if (livingentity != null && livingentity.isAlive()) {
                this.target = livingentity;
                return true;
            } else {
                return false;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse() || this.target.isAlive() && !this.loder.getNavigation().isDone();
        }
    }
}
