package com.littleezra.ethereal.loot;

import com.littleezra.ethereal.Ethereal;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZORS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Ethereal.MODID);

    public static void register(IEventBus eventBus){
        LOOT_MODIFIER_SERIALIZORS.register(eventBus);
    }
}
