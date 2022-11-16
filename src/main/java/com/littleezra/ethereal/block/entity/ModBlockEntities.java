package com.littleezra.ethereal.block.entity;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Ethereal.MODID);

    public static final RegistryObject<BlockEntityType<SharpshooterBlockEntity>> SHARPSHOOTER = BLOCK_ENTITES.register("sharpshooter",
            () -> BlockEntityType.Builder.of(SharpshooterBlockEntity::new, ModBlocks.SHARPSHOOTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SharpshooterBlockEntity>> AMBER_BLOCK = BLOCK_ENTITES.register("amber_block",
            () -> BlockEntityType.Builder.of(SharpshooterBlockEntity::new, ModBlocks.SHARPSHOOTER.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITES.register(eventBus);
    }
}
