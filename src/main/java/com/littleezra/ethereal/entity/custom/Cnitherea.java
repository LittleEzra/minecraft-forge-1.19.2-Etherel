package com.littleezra.ethereal.entity.custom;

import com.littleezra.ethereal.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.xml.crypto.Data;

public class Cnitherea extends FlyingMob implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Float> DATA_HOVER_POSITION = SynchedEntityData.defineId(Cnitherea.class, EntityDataSerializers.FLOAT);

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {

        this.entityData.set(DATA_HOVER_POSITION, (float)position().y());

        return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
    }

    public void addAdditionalSaveData(CompoundTag p_149385_) {
        super.addAdditionalSaveData(p_149385_);
        p_149385_.putFloat("HoverPosition", (float)this.getHoverPos());
    }

    public void readAdditionalSaveData(CompoundTag p_149373_) {
        super.readAdditionalSaveData(p_149373_);
        this.entityData.set(DATA_HOVER_POSITION, p_149373_.getFloat("HoverPosition"));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HOVER_POSITION, 20F);
    }

    public static AttributeSupplier setAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D).build();
    }

    public Cnitherea(EntityType<? extends FlyingMob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event){
        return PlayState.CONTINUE;
    }
    private <E extends IAnimatable>PlayState jumpPredicate(AnimationEvent<E> animationEvent)
    {
        animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("cnitherea.animation.move", false));
        return PlayState.CONTINUE;
    }
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new CnithereaJumpGoal(this));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        data.addAnimationController(new AnimationController(this, "jumpController",
                0, this::jumpPredicate));
    }

    @Override
    public void travel(Vec3 p_20818_) {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {

            double d0 = 0.02D;

            if (this.isInWater()) {
                this.moveRelative(0.02F, p_20818_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double)0.8F));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, p_20818_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            } else {
                BlockPos ground = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
                float f = 0.91F;
                if (this.onGround) {
                    f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
                }

                float f1 = 0.16277137F / (f * f * f);
                f = 0.91F;
                if (this.onGround) {
                    f = this.level.getBlockState(ground).getFriction(this.level, ground, this) * 0.91F;
                }

                this.moveRelative(this.onGround ? 0.1F * f1 : 0.02F, p_20818_);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double)f));
            }
            if (!this.isNoGravity()) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -d0 / 4.0D, 0.0D));
            }
        }
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    protected SoundEvent getMoveSound(){ return ModSounds.ETHEREAL_JELLYFISH_MOVE.get(); }

    @Override
    protected float getSoundVolume() {
        return 0.2F;
    }

    public double getHoverPos(){
        return (double)this.entityData.get(DATA_HOVER_POSITION);
    }

    static class CnithereaJumpGoal extends Goal
    {
        private final Cnitherea cnitherea;
        public double hoverPos = 20D;

        public CnithereaJumpGoal(Cnitherea entity)
        {
            this.cnitherea = entity;
        }

        @Override
        public boolean canUse() { return true; }

        public boolean canContinueToUse() {
            return true;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void start() {
            hoverPos = cnitherea.getHoverPos();
        }

        @Override
        public void tick()
        {
            if (cnitherea.position().y() < hoverPos)
            {
                cnitherea.setDeltaMovement(cnitherea.getDeltaMovement().add(0.0D, 0.7D, 0.0D));
            }
        }
    }
}