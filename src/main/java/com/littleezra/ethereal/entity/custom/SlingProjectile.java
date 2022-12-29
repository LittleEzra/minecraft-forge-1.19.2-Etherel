package com.littleezra.ethereal.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class SlingProjectile extends Projectile {
    private static final EntityDataAccessor<Byte> ID_FLAGS = SynchedEntityData.defineId(SlingProjectile.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(SlingProjectile.class, EntityDataSerializers.ITEM_STACK);

    public SlingProjectile(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
    }
    public SlingProjectile(EntityType<? extends Projectile> type, Level level, ItemStack item) {
        super(type, level);
        this.setItem(item);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(ID_FLAGS, (byte)0);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (!this.getItem().isEmpty()) {
            nbt.put("Item", this.getItem().save(new CompoundTag()));
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        CompoundTag compoundtag = nbt.getCompound("Item");
        this.setItem(ItemStack.of(compoundtag));
        if (this.getItem().isEmpty()) {
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        if (this.getItem().getItem() instanceof PotionItem){
            makeAreaOfEffectCloud(this.getItem(), PotionUtils.getPotion(this.getItem()));
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        Entity entity = result.getEntity();

        result.getEntity().hurt(DamageSource.thrown(this, getOwner()), getDamage());

        if (this.getItem().getItem() instanceof PotionItem && entity instanceof LivingEntity livingEntity){
            for(MobEffectInstance mobeffectinstance : PotionUtils.getMobEffects(this.getItem())) {
                if (mobeffectinstance.getEffect().isInstantenous()) {
                    mobeffectinstance.getEffect().applyInstantenousEffect(livingEntity, getOwner(), livingEntity, mobeffectinstance.getAmplifier(), 1.0D);
                } else {
                    livingEntity.addEffect(new MobEffectInstance(mobeffectinstance));
                }
            }
        }
    }

    private float getDamage(){
        return 2f;
    }

    private void makeAreaOfEffectCloud(ItemStack item, Potion potion) {
        AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            areaeffectcloud.setOwner((LivingEntity)entity);
        }

        areaeffectcloud.setRadius(3.0F);
        areaeffectcloud.setRadiusOnUse(-0.5F);
        areaeffectcloud.setWaitTime(10);
        areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float)areaeffectcloud.getDuration());
        areaeffectcloud.setPotion(potion);

        for(MobEffectInstance mobeffectinstance : PotionUtils.getCustomEffects(item)) {
            areaeffectcloud.addEffect(new MobEffectInstance(mobeffectinstance));
        }

        CompoundTag compoundtag = item.getTag();
        if (compoundtag != null && compoundtag.contains("CustomPotionColor", 99)) {
            areaeffectcloud.setFixedColor(compoundtag.getInt("CustomPotionColor"));
        }

        this.level.addFreshEntity(areaeffectcloud);
    }

    public ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM);
    }

    public void setItem(ItemStack itemStack) {
        this.getEntityData().set(DATA_ITEM, itemStack);
    }
}

