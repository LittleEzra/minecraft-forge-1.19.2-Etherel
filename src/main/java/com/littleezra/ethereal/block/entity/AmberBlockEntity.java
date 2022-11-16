package com.littleezra.ethereal.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class AmberBlockEntity extends BlockEntity {

    @Nullable
    private Entity displayEntity;
    private SpawnData nextSpawnData = new SpawnData();

    public void setEntityId(EntityType<?> p_45463_) {
        this.nextSpawnData.getEntityToSpawn().putString("id", ForgeRegistries.ENTITY_TYPES.getKey(p_45463_).toString());
    }

    public AmberBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

}
