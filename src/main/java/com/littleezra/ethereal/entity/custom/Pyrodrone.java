package com.littleezra.ethereal.entity.custom;

import com.littleezra.ethereal.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;

public class Pyrodrone extends FlyingMob implements IAnimatable, RangedAttackMob {
    public AnimationFactory factory = new AnimationFactory(this);

    public Pyrodrone(EntityType<? extends FlyingMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new Pyrodrone.PyrodroneMoveControl(this);
    }

    public static AttributeSupplier setAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.FLYING_SPEED, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 1D)
                .add(Attributes.FOLLOW_RANGE, 10D).build();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    public PlayState predicate(AnimationEvent event){
        if (event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("flaron.animation.move"));

            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("flaron.animation.idle"));

        return PlayState.CONTINUE;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new PyrodroneAttackGoal(this, 1D, 10, 4f));
        this.goalSelector.addGoal(4, new PyrodroneLookGoal(this));
        this.goalSelector.addGoal(5, new Pyrodrone.RandomFloatGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (entity) -> {
            return Math.abs(entity.getY() - this.getY()) <= 4.0D;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float damage) {
        target.hurt(DamageSource.mobAttack(this), 1f);
        this.playSound(ModSounds.PYRODRONE_SHOOT.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.SMALL_ROBOT_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.SMALL_ROBOT_HIT.get();
    }

    static class PyrodroneLookGoal extends Goal {
        private final Pyrodrone pyrodrone;

        public PyrodroneLookGoal(Pyrodrone pyrodrone) {
            this.pyrodrone = pyrodrone;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return true;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.pyrodrone.getTarget() == null) {
                Vec3 vec3 = this.pyrodrone.getDeltaMovement();
                this.pyrodrone.setYRot(-((float)Mth.atan2(vec3.x, vec3.z)) * (180F / (float)Math.PI));
                this.pyrodrone.yBodyRot = this.pyrodrone.getYRot();
            } else {
                LivingEntity livingentity = this.pyrodrone.getTarget();
                double d0 = 64.0D;
                if (livingentity.distanceToSqr(this.pyrodrone) < 4096.0D) {
                    double d1 = livingentity.getX() - this.pyrodrone.getX();
                    double d2 = livingentity.getZ() - this.pyrodrone.getZ();
                    this.pyrodrone.setYRot(-((float)Mth.atan2(d1, d2)) * (180F / (float)Math.PI));
                    this.pyrodrone.yBodyRot = this.pyrodrone.getYRot();
                }
            }

        }
    }
    private class RandomFloatGoal extends Goal{
        protected final Pyrodrone pyrodrone;

        public RandomFloatGoal(Pyrodrone pyrodrone) {
            this.pyrodrone = pyrodrone;
        }

        @Override
        public boolean canUse() {
            MoveControl movecontrol = this.pyrodrone.getMoveControl();
            if (!movecontrol.hasWanted()) {
                return true;
            } else {
                double xDistance = movecontrol.getWantedX() - this.pyrodrone.getX();
                double yDistance = movecontrol.getWantedY() - this.pyrodrone.getY();
                double zDistance = movecontrol.getWantedZ() - this.pyrodrone.getZ();
                double d3 = xDistance * xDistance + yDistance * yDistance + zDistance * zDistance;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void start() {
            RandomSource randomsource = this.pyrodrone.getRandom();
            double d0 = this.pyrodrone.getX() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 4.0F);
            double d1 = this.pyrodrone.getY() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 4.0F);
            double d2 = this.pyrodrone.getZ() + (double)((randomsource.nextFloat() * 2.0F - 1.0F) * 4.0F);
            this.pyrodrone.getMoveControl().setWantedPosition(d0, d1, d2, 1.0D);
        }
    }
    static class PyrodroneMoveControl extends MoveControl {
        private final Pyrodrone pyrodrone;
        private int floatDuration;

        public PyrodroneMoveControl(Pyrodrone pyrodrone) {
            super(pyrodrone);
            this.pyrodrone = pyrodrone;
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.pyrodrone.getRandom().nextInt(5) + 2;
                    Vec3 dir = new Vec3(this.wantedX - this.pyrodrone.getX(), this.wantedY - this.pyrodrone.getY(), this.wantedZ - this.pyrodrone.getZ());
                    double d0 = dir.length();
                    dir = dir.normalize();
                    if (this.canReach(dir, Mth.ceil(d0))) {
                        this.pyrodrone.setDeltaMovement(this.pyrodrone.getDeltaMovement().add(dir.scale(0.1D)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }
            }
        }

        private boolean canReach(Vec3 p_32771_, int p_32772_) {
            AABB aabb = this.pyrodrone.getBoundingBox();

            for(int i = 1; i < p_32772_; ++i) {
                aabb = aabb.move(p_32771_);
                if (!this.pyrodrone.level.noCollision(this.pyrodrone, aabb)) {
                    return false;
                }
            }

            return true;
        }
    }
    static class PyrodroneAttackGoal extends Goal{
        private final Mob mob;
        private final Pyrodrone pyrodrone;
        @javax.annotation.Nullable
        private LivingEntity target;
        private int attackTime = -1;
        private final double speedModifier;
        private int seeTime;
        private final int attackIntervalMin;
        private final int attackIntervalMax;
        private final float attackRadius;
        private final float attackRadiusSqr;

        public PyrodroneAttackGoal(Pyrodrone pyrodrone, double speedModifier, int attackInterval, float attackRadius) {
            this(pyrodrone, speedModifier, attackInterval, attackInterval, attackRadius);
        }

        public PyrodroneAttackGoal(Pyrodrone pyrodrone, double speedModifier, int attackIntervalMin, int attackIntervalMax, float attackRadius) {
            if (pyrodrone == null) {
                throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
            } else {
                this.pyrodrone = pyrodrone;
                this.mob = (Mob)pyrodrone;
                this.speedModifier = speedModifier;
                this.attackIntervalMin = attackIntervalMin;
                this.attackIntervalMax = attackIntervalMax;
                this.attackRadius = attackRadius;
                this.attackRadiusSqr = attackRadius * attackRadius;
                this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            }
        }

        public boolean canUse() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity != null && livingentity.isAlive()) {
                this.target = livingentity;
                return true;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return this.canUse() || this.target.isAlive() && !this.mob.getNavigation().isDone();
        }

        public void stop() {
            this.target = null;
            this.seeTime = 0;
            this.attackTime = -1;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
            boolean flag = this.mob.getSensing().hasLineOfSight(this.target);
            if (flag) {
                ++this.seeTime;
            } else {
                this.seeTime = 0;
            }

            if (!(d0 > (double)this.attackRadiusSqr) && this.seeTime >= 5) {
                this.mob.getNavigation().stop();
            } else {
                this.mob.getNavigation().moveTo(this.target, this.speedModifier);
            }

            this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
            if (--this.attackTime == 0) {
                if (!flag) {
                    return;
                }

                float f = (float)Math.sqrt(d0) / this.attackRadius;
                float f1 = Mth.clamp(f, 0.1F, 1.0F);
                this.pyrodrone.performRangedAttack(this.target, f1);
                this.attackTime = Mth.floor(f * (float)(this.attackIntervalMax - this.attackIntervalMin) + (float)this.attackIntervalMin);
            } else if (this.attackTime < 0) {
                this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double)this.attackRadius, (double)this.attackIntervalMin, (double)this.attackIntervalMax));
            }

        }
    }
}
