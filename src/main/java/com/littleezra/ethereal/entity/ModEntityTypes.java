package com.littleezra.ethereal.entity;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Ethereal.MODID);

    public static final RegistryObject<EntityType<Cnitherea>> CNITHEREA = ENTITY_TYPES.register("cnitherea",
            () -> EntityType.Builder.of(Cnitherea::new, MobCategory.CREATURE).sized(2f, 1.5f)
                    .build(new ResourceLocation(Ethereal.MODID, "cnitherea").toString()));

    public static final RegistryObject<EntityType<TotemGolem>> TOTEM_GOLEM = ENTITY_TYPES.register("totem_golem",
            () -> EntityType.Builder.of(TotemGolem::new, MobCategory.CREATURE).sized(1f, 3.625f)
                    .build(new ResourceLocation(Ethereal.MODID, "totem_golem").toString()));

    public static final RegistryObject<EntityType<Fairyfly>> FAIRYFLY = ENTITY_TYPES.register("fairyfly",
            () -> EntityType.Builder.of(Fairyfly::new, MobCategory.AMBIENT).sized(0.375f, 0.375f)
                    .build(new ResourceLocation(Ethereal.MODID, "fairyfly").toString()));

    public static final RegistryObject<EntityType<Bushhog>> BUSHHOG = ENTITY_TYPES.register("bushhog",
            () -> EntityType.Builder.of(Bushhog::new, MobCategory.CREATURE).sized(1f, 0.625f)
                    .build(new ResourceLocation(Ethereal.MODID, "bushhog").toString()));

    public static final RegistryObject<EntityType<Snatcher>> SNATCHER = ENTITY_TYPES.register("snatcher",
            () -> EntityType.Builder.of(Snatcher::new, MobCategory.CREATURE).sized(0.5f, 0.5625f)
                    .build(new ResourceLocation(Ethereal.MODID, "snatcher").toString()));

    public static final RegistryObject<EntityType<Pyrodrone>> PYRODRONE = ENTITY_TYPES.register("pyrodrone",
            () -> EntityType.Builder.of(Pyrodrone::new, MobCategory.CREATURE).sized(0.875f, 0.1875f)
                    .build(new ResourceLocation(Ethereal.MODID, "pyrodrone").toString()));

    public static final RegistryObject<EntityType<Loder>> LODER = ENTITY_TYPES.register("loder",
            () -> EntityType.Builder.of(Loder::new, MobCategory.CREATURE).sized( 1.5f, 1.25f)
                    .build(new ResourceLocation(Ethereal.MODID, "loder").toString()));

    public static final RegistryObject<EntityType<Keeper>> KEEPER = ENTITY_TYPES.register("keeper",
            () -> EntityType.Builder.of(Keeper::new, MobCategory.CREATURE).sized( 0.375f, 1.5f)
                    .build(new ResourceLocation(Ethereal.MODID, "keeper").toString()));


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
