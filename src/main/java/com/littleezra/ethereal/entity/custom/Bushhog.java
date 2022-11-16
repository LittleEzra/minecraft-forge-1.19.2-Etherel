package com.littleezra.ethereal.entity.custom;

import com.google.common.collect.ImmutableList;
import com.littleezra.ethereal.player.PlayerFairyAggressor;
import com.littleezra.ethereal.player.PlayerFairyAggressorProvider;
import com.littleezra.ethereal.world.entity.animal.bushhog.BushhogAi;
import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
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

public class Bushhog extends PathfinderMob implements IAnimatable, IFairy {

    protected static final ImmutableList<SensorType<? extends Sensor<? super Bushhog>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_PLAYERS
    );
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(
            MemoryModuleType.PATH,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.NEAREST_PLAYERS
    );
    private static final EntityDataAccessor<Boolean> DATA_IS_HIDING = SynchedEntityData.defineId(Fairyfly.class, EntityDataSerializers.BOOLEAN);

    public static final DamageSource BUSHHOG_DAMAGE = new DamageSource("bushhog_damage");

    private AnimationFactory factory = new AnimationFactory(this);

    @Override
    public float getSpeed() {
        if (closeToAggressor())
            return 0.3F;
        return super.getSpeed();
    }

    public boolean isHiding(){
        return this.entityData.get(DATA_IS_HIDING);
    }
    public void setIsHiding(boolean value){
        this.entityData.set(DATA_IS_HIDING, value);
    }

    public boolean canHide(){
        boolean flag = false;

        boolean nearLiquid = level.getBlockStates(getBoundingBox().inflate(2)).anyMatch((state) ->{
            return state.is(Blocks.WATER) || state.is(Blocks.LAVA);
        });

        flag = !nearLiquid && !closeToAggressor();

        return flag;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {

        setIsHiding(false);

        return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
    }
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("IsHiding", this.isHiding());
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(DATA_IS_HIDING, nbt.getBoolean("IsHiding"));
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_HIDING, false);
    }

    public static AttributeSupplier setAttributes(){
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1F)
                .build();
    }
    protected Brain.Provider<Bushhog> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    protected Brain<?> makeBrain(Dynamic<?> p_218344_) {
        return BushhogAi.makeBrain(this.brainProvider().makeBrain(p_218344_));
    }

    public Brain<Bushhog> getBrain() {
        return (Brain<Bushhog>)super.getBrain();
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
    public void tick() {
        super.tick();
        if (isHiding()){
            List<Entity> list = this.level.getEntities(this, this.getBoundingBox());
            for (int i = 0; i < list.size(); i++){
                if (list.get(i) instanceof LivingEntity livingEntity){
                    livingEntity.hurt(BUSHHOG_DAMAGE, 2f);
                }
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        source.getDirectEntity().hurt(BUSHHOG_DAMAGE, 2f);

        return super.hurt(source, damage);
    }

    public Bushhog(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new MoveControl(this);
    }

    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event){

        if (isHiding()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("bushhog.animation.hide", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        if (event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("bushhog.animation.move", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("bushhog.animation.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected float getSoundVolume() {
        return 0.2F;
    }

    protected void customServerAiStep() {
        this.level.getProfiler().push("bushhogBrain");
        this.getBrain().tick((ServerLevel)this.level, this);
        this.level.getProfiler().pop();
        this.level.getProfiler().push("bushhogActivityUpdate");
        BushhogAi.updateActivity(this);
        this.level.getProfiler().pop();
        super.customServerAiStep();
    }
}
