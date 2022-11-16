package com.littleezra.ethereal.entity;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Bushhog;
import com.littleezra.ethereal.entity.custom.Cnitherea;
import com.littleezra.ethereal.entity.custom.Fairyfly;
import com.littleezra.ethereal.entity.custom.TotemGolem;
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


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
