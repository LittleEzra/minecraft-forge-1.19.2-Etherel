package com.littleezra.ethereal.entity.custom;

import com.google.common.collect.ImmutableList;
import com.littleezra.ethereal.player.PlayerFairyAggressor;
import com.littleezra.ethereal.player.PlayerFairyAggressorProvider;
import com.littleezra.ethereal.world.entity.animal.fairyfly.FairyflyAi;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class Fairyfly extends PathfinderMob implements IAnimatable, IFairy
{
    protected static final ImmutableList<SensorType<? extends Sensor<? super Fairyfly>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_PLAYERS
    );
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.PATH,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.NEAREST_PLAYERS
    );

    private final AnimationFactory factory = new AnimationFactory(this);

    private static final EntityDataAccessor<Boolean> DATA_IS_SITTING = SynchedEntityData.defineId(Fairyfly.class, EntityDataSerializers.BOOLEAN);

    public boolean getIsSitting(){
        return this.entityData.get(DATA_IS_SITTING);
    }

    public void setIsSitting(boolean value){
        this.entityData.set(DATA_IS_SITTING, value);
    }

    public Fairyfly(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    public boolean canBeLeashed(Player p_27406_) {
        return false;
    }

    public boolean causeFallDamage(float v, float v1, DamageSource damageSource) {
        return false;
    }

    protected void checkFallDamage(double v, boolean b, BlockState state, BlockPos pos) {
    }

    protected Brain.Provider<Fairyfly> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> p_218344_) {
        return FairyflyAi.makeBrain(this.brainProvider().makeBrain(p_218344_));
    }

    public Brain<Fairyfly> getBrain() {
        return (Brain<Fairyfly>)super.getBrain();
    }

    public boolean closeToAggressor(){

        List<Entity> entities = level.getEntities(this, getBoundingBox().inflate(8));

        if (entities.size() > 0){
            for (int i = 0; i < entities.size(); i++){
                if (entities.get(i) instanceof Player player){
                    return IFairy.playerIsAggressor(player);
                }
            }
        }
        return false;
    }

    @Override
    public float getSpeed() {
        if (closeToAggressor())
            return 0.5F;
        return super.getSpeed();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {

        setIsSitting(false);

        return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
    }
    public void addAdditionalSaveData(CompoundTag p_149385_) {
        super.addAdditionalSaveData(p_149385_);
        p_149385_.putBoolean("IsSitting", this.getIsSitting());
    }

    public void readAdditionalSaveData(CompoundTag p_149373_) {
        super.readAdditionalSaveData(p_149373_);
        this.entityData.set(DATA_IS_SITTING, p_149373_.getBoolean("IsSitting"));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_SITTING, false);
    }

    public static AttributeSupplier setAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1F).add(Attributes.FLYING_SPEED, 0.1F)
                .build();
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event)
    {
        if (this.getSpeed() > 0){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("fairyfly.animation.move", ILoopType.EDefaultLoopTypes.LOOP));
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("fairyfly.animation.sit", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void aiStep() {
        this.setIsSitting(this.getSpeed() <= 0);
        super.aiStep();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    protected void customServerAiStep() {
        this.level.getProfiler().push("fairyflyBrain");
        this.getBrain().tick((ServerLevel)this.level, this);
        this.level.getProfiler().pop();
        this.level.getProfiler().push("fairyflyActivityUpdate");
        FairyflyAi.updateActivity(this);
        this.level.getProfiler().pop();
        super.customServerAiStep();
    }

    protected PathNavigation createNavigation(Level p_218342_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_218342_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    public void travel(Vec3 direction) {
        if (this.isEffectiveAi() || this.isControlledByLocalInstance()) {
            if (this.isInWater()) {
                this.moveRelative(0.02F, direction);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.8F));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, direction);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D));
            } else {
                this.moveRelative(this.getSpeed(), direction);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale(0.91F));
            }
        }
    }

    public Iterable<BlockPos> iteratePathfindingStartNodeCandidatePositions() {
        AABB aabb = this.getBoundingBox();
        int i = Mth.floor(aabb.minX - 0.5D);
        int j = Mth.floor(aabb.maxX + 0.5D);
        int k = Mth.floor(aabb.minZ - 0.5D);
        int l = Mth.floor(aabb.maxZ + 0.5D);
        int i1 = Mth.floor(aabb.minY - 0.5D);
        int j1 = Mth.floor(aabb.maxY + 0.5D);
        return BlockPos.betweenClosed(i, i1, k, j, j1, l);
    }
}
