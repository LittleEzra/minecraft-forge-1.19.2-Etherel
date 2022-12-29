package com.littleezra.ethereal.entity.custom;

import com.littleezra.ethereal.entity.ModEntityTypes;
import com.littleezra.ethereal.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
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

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class TotemGolem extends AbstractGolem implements IAnimatable, OwnableEntity {

    private AnimationFactory factory = new AnimationFactory(this);
    private static final EntityDataAccessor<Boolean> DATA_IS_ROOTED = SynchedEntityData.defineId(TotemGolem.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_ID = SynchedEntityData.defineId(TotemGolem.class, EntityDataSerializers.OPTIONAL_UUID);
    private boolean orderedToSit;
    private boolean hasPlayedRoot;

    private static Supplier<MobEffectInstance> effect = () -> new MobEffectInstance(MobEffects.REGENERATION, 100, 2);

    public TotemGolem(EntityType<? extends AbstractGolem> p_27508_, Level p_27509_) {
        super(p_27508_, p_27509_);
    }
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {

        this.entityData.set(DATA_OWNER_ID, Optional.empty());
        this.entityData.set(DATA_IS_ROOTED, false);
        this.orderedToSit = false;

        return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
    }

    @Override
    protected float getSoundVolume() {
        return 0.5f;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.TOTEM_GOLEM_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_27517_) {
        return ModSounds.TOTEM_GOLEM_HIT.get();
    }

    @Override
    protected void playStepSound(BlockPos p_20135_, BlockState p_20136_) {
        this.playSound(SoundEvents.WOOD_STEP, 1.0F, 1.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(2, new RootGoal(this));
        this.goalSelector.addGoal(3, new TotemGolemRandomStroll(this, 1.0D));
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putBoolean("Rooted", this.isRooted());
        compoundTag.putBoolean("Sitting", orderedToSit);
        if (this.getOwnerUUID() != null) {
            compoundTag.putUUID("Owner", this.getOwnerUUID());
        }
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);

        UUID uuid;
        if (compoundTag.hasUUID("Owner")) {
            uuid = compoundTag.getUUID("Owner");
        } else {
            String s = compoundTag.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
            try {
                this.setOwnerUUID(uuid);
            } catch (Throwable throwable) {
            }
        }

        this.entityData.set(DATA_IS_ROOTED, compoundTag.getBoolean("Rooted"));
        this.orderedToSit = compoundTag.getBoolean("Sitting");
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_ROOTED, false);
        this.entityData.define(DATA_OWNER_ID, Optional.empty());
    }

    @Override
    public void tick() {
        super.tick();

        if (isRooted() && getOwner() != null){
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(8));

            for (LivingEntity entity : entities){
                if (!entity.hasEffect(MobEffects.REGENERATION)){
                    if (getOwner().getTeam() != null && entity.getTeam() == getOwner().getTeam()){
                        entity.addEffect(effect.get());
                    } else if (getOwner().getTeam() == null){
                        getOwner().addEffect(effect.get());
                    }
                }
            }
        }
    }

    public boolean isRooted(){
        return this.entityData.get(DATA_IS_ROOTED);
    }
    public void setRooted(boolean value){
        this.entityData.set(DATA_IS_ROOTED, value);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(DATA_OWNER_ID, Optional.ofNullable(uuid));
    }
    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNER_ID).orElse((UUID)null);
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerUUID();
            return uuid == null ? null : this.level.getPlayerByUUID(uuid);
        } catch (IllegalArgumentException illegalargumentexception) {
            return null;
        }
    }


    public static AttributeSupplier setAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 75.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).build();
    }



    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        ILoopType.EDefaultLoopTypes loop = ILoopType.EDefaultLoopTypes.LOOP;
        ILoopType.EDefaultLoopTypes playOnce = ILoopType.EDefaultLoopTypes.PLAY_ONCE;

        if (this.isRooted()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("totem_golem.animation.rooted", loop));
            return PlayState.CONTINUE;
        }
        else if (event.isMoving()){
            this.setPlayedRoot(false);
            event.getController().setAnimation(new AnimationBuilder().addAnimation("totem_golem.animation.walk", loop));
            return PlayState.CONTINUE;
        }

        this.setPlayedRoot(false);

        event.getController().setAnimation(new AnimationBuilder().addAnimation("totem_golem.animation.idle", loop));
        return PlayState.CONTINUE;
    }

    private PlayState rootPredicate(AnimationEvent event) {

        if (this.isRooted() && !this.hasPlayedRoot() && event.getController().getAnimationState().equals(AnimationState.Stopped))
        {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("totem_golem.animation.root", ILoopType.EDefaultLoopTypes.PLAY_ONCE));

            this.setPlayedRoot(true);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
        data.addAnimationController(new AnimationController(this, "rootController",
                0, this::rootPredicate));
    }

    @Override
    public float getSpeed() {
        return isRooted() ? 0f : super.getSpeed();
    }

    public boolean isOrderedToSit()
    {
        return orderedToSit;
    }

    public boolean hasPlayedRoot(){
        return this.hasPlayedRoot;
    }
    public void setPlayedRoot(boolean value){
        this.hasPlayedRoot = value;
    }

    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!itemstack.is(Items.POPPY) && !itemstack.is(Items.WITHER_ROSE)) {
            return InteractionResult.PASS;
        } else if (itemstack.is(Items.POPPY) && this.getOwner() == null){
            this.setOwnerUUID(player.getUUID());

            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else if (this.getOwner() != null && player.getUUID() == this.getOwnerUUID()){

            this.setOwnerUUID(null);
            this.setRooted(false);

            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public boolean checkSpawnObstruction(LevelReader p_28853_) {
        BlockPos blockpos = this.blockPosition();
        BlockPos blockposbelow = blockpos.below();
        BlockState blockstate = p_28853_.getBlockState(blockposbelow);
        if (!blockstate.entityCanStandOn(p_28853_, blockposbelow, this)) {
            return false;
        } else {
            for(int i = 1; i < 3; ++i) {
                BlockPos blockpos2 = blockpos.above(i);
                BlockState blockstate1 = p_28853_.getBlockState(blockpos2);
                if (!NaturalSpawner.isValidEmptySpawnBlock(p_28853_, blockpos2, blockstate1, blockstate1.getFluidState(), ModEntityTypes.TOTEM_GOLEM.get())) {
                    return false;
                }
            }

            return NaturalSpawner.isValidEmptySpawnBlock(p_28853_, blockpos, p_28853_.getBlockState(blockpos), Fluids.EMPTY.defaultFluidState(), ModEntityTypes.TOTEM_GOLEM.get()) && p_28853_.isUnobstructed(this);
        }
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    static class RootGoal extends Goal
    {
        private final TotemGolem golem;
        private LivingEntity owner;

        public RootGoal(TotemGolem entity){
            this.golem = entity;
            owner = entity.getOwner();
        }

        public boolean canContinueToUse() {
            return golem.getOwner() != null;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void start() {
            owner = golem.getOwner();
        }

        @Override
        public void tick() {
            owner = golem.getOwner();

            if (owner != null) {
                if (owner.getHealth() < owner.getMaxHealth() * 0.8D){
                    golem.setRooted(true);
                } else{
                    golem.setPlayedRoot(false);
                    golem.setRooted(false);
                }
            } else{
                golem.setPlayedRoot(false);
                golem.setRooted(false);
            }
        }
    }

    static class FollowOwnerGoal extends Goal {
        public static final int TELEPORT_WHEN_DISTANCE_IS = 12;
        private static final int MIN_HORIZONTAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 2;
        private static final int MAX_HORIZONTAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 3;
        private static final int MAX_VERTICAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 1;
        private final TotemGolem golem;
        private LivingEntity owner;
        private final LevelReader level;
        private final double speedModifier;
        private final PathNavigation navigation;
        private int timeToRecalcPath;
        private final float stopDistance;
        private final float startDistance;
        private float oldWaterCost;
        private final boolean canFly;

        public FollowOwnerGoal(TotemGolem p_25294_, double p_25295_, float p_25296_, float p_25297_, boolean p_25298_) {
            this.golem = p_25294_;
            this.level = p_25294_.level;
            this.speedModifier = p_25295_;
            this.navigation = p_25294_.getNavigation();
            this.startDistance = p_25296_;
            this.stopDistance = p_25297_;
            this.canFly = p_25298_;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            if (!(p_25294_.getNavigation() instanceof GroundPathNavigation) && !(p_25294_.getNavigation() instanceof FlyingPathNavigation)) {
                throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
            }
        }

        public boolean canUse() {
            LivingEntity livingentity = this.golem.getOwner();
            if (livingentity == null) {
                return false;
            } else if (livingentity.isSpectator()) {
                return false;
            } else if (this.golem.isOrderedToSit()) {
                return false;
            } else if (this.golem.isRooted()) {
                return false;
            } else if (this.golem.distanceToSqr(livingentity) < (double)(this.startDistance * this.startDistance)) {
                return false;
            } else {
                this.owner = livingentity;
                return true;
            }
        }

        public boolean canContinueToUse() {
            if (this.navigation.isDone()) {
                return false;
            } else if (golem.isOrderedToSit()){
                return false;
            } else {
                return !(this.golem.distanceToSqr(this.owner) <= (double)(this.stopDistance * this.stopDistance));
            }
        }

        public void start() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.golem.getPathfindingMalus(BlockPathTypes.WATER);
            this.golem.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        }

        public void stop() {
            this.owner = null;
            this.navigation.stop();
            this.golem.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        }

        public void tick() {
            this.golem.getLookControl().setLookAt(this.owner, 10.0F, 40f);
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                if (!this.golem.isLeashed() && !this.golem.isPassenger()) {
                    this.golem.setRooted(false);
                    if (this.golem.distanceToSqr(this.owner) >= 144.0D) {
                        this.teleportToOwner();
                    } else {
                        this.navigation.moveTo(this.owner, this.speedModifier);
                    }

                }
            }
        }

        private void teleportToOwner() {
            BlockPos blockpos = this.owner.blockPosition();

            for(int i = 0; i < 10; ++i) {
                int j = this.randomIntInclusive(-3, 3);
                int k = this.randomIntInclusive(-1, 1);
                int l = this.randomIntInclusive(-3, 3);
                boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
                if (flag) {
                    return;
                }
            }

        }

        private boolean maybeTeleportTo(int p_25304_, int p_25305_, int p_25306_) {
            if (Math.abs((double)p_25304_ - this.owner.getX()) < 2.0D && Math.abs((double)p_25306_ - this.owner.getZ()) < 2.0D) {
                return false;
            } else if (!this.canTeleportTo(new BlockPos(p_25304_, p_25305_, p_25306_))) {
                return false;
            } else {
                this.golem.moveTo((double)p_25304_ + 0.5D, (double)p_25305_, (double)p_25306_ + 0.5D, this.golem.getYRot(), this.golem.getXRot());
                this.navigation.stop();
                return true;
            }
        }

        private boolean canTeleportTo(BlockPos p_25308_) {
            BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, p_25308_.mutable());
            if (blockpathtypes != BlockPathTypes.WALKABLE) {
                return false;
            } else {
                BlockState blockstate = this.level.getBlockState(p_25308_.below());
                if (!this.canFly && blockstate.getBlock() instanceof LeavesBlock) {
                    return false;
                } else {
                    BlockPos blockpos = p_25308_.subtract(this.golem.blockPosition());
                    return this.level.noCollision(this.golem, this.golem.getBoundingBox().move(blockpos));
                }
            }
        }

        private int randomIntInclusive(int p_25301_, int p_25302_) {
            return this.golem.getRandom().nextInt(p_25302_ - p_25301_ + 1) + p_25301_;
        }
    }

    public class TotemGolemRandomStroll extends RandomStrollGoal {
        public static final float PROBABILITY = 0.001F;
        protected final float probability;

        public TotemGolemRandomStroll(PathfinderMob p_25987_, double p_25988_) {
            this(p_25987_, p_25988_, 0.001F);
        }

        public TotemGolemRandomStroll(PathfinderMob p_25990_, double p_25991_, float p_25992_) {
            super(p_25990_, p_25991_);
            this.probability = p_25992_;
        }

        @Override
        public boolean canUse() {
            if (isRooted()){
                return false;
            }
            return super.canUse();
        }

        @javax.annotation.Nullable
        protected Vec3 getPosition() {
            if (this.mob.isInWaterOrBubble()) {
                Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
                return vec3 == null ? super.getPosition() : vec3;
            } else {
                return this.mob.getRandom().nextFloat() >= this.probability ? LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
            }
        }
    }
}
