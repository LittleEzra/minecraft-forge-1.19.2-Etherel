package com.littleezra.ethereal.entity.custom;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Cnitherea extends FlyingMob implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Float> DATA_HOVER_POSITION = SynchedEntityData.defineId(Cnitherea.class, EntityDataSerializers.FLOAT);

    @Override
    public void tick() {
        super.tick();
        List<Entity> list = this.level.getEntities(this, this.getBoundingBox());
        for (int i = 0; i < list.size(); i++){
            if (list.get(i) instanceof LivingEntity livingEntity && !(livingEntity instanceof Cnitherea)){
                livingEntity.setDeltaMovement(
                        livingEntity.getDeltaMovement().x,
                        0.8D,
                        livingEntity.getDeltaMovement().z
                );

                if (livingEntity instanceof Player player && Ethereal.getServerPlayer(player) != null){
                    Ethereal.CNITHEREA_BOUNCE.trigger((ServerPlayer) player);
                }
            }
        }
    }

    public static boolean canSpawn(EntityType<Cnitherea> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random){
        boolean flag = pos.getY() > 40 && level.isEmptyBlock(pos) && level.isEmptyBlock(pos.below());
        return flag;
    }

    @Override
    public void push(Entity p_21294_) {
    }

    @Override
    protected void pushEntities() {
    }

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
        event.getController().setAnimation(new AnimationBuilder().addAnimation("cnitherea.animation.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }
    private <E extends IAnimatable>PlayState jumpPredicate(AnimationEvent<E> event)
    {
        if (this.getY() < this.getHoverPos() && event.getController().getAnimationState().equals(AnimationState.Stopped)){
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("cnitherea.animation.move", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
        }
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
        private final int BOUNCE_DELAY = 10;
        private int delayTime;

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
            if (cnitherea.position().y() < hoverPos - 0.5D)
            {
                cnitherea.setDeltaMovement(cnitherea.getDeltaMovement().add(0.0D, 0.7D, 0.0D));
            }
        }
    }
}
