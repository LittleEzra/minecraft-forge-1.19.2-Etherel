package com.littleezra.ethereal.world.effect;

import com.littleezra.ethereal.Ethereal;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Ethereal.MODID);

    public static final RegistryObject<MobEffect> SIPHON = MOB_EFFECTS.register("siphon", () ->
            new SiphonEffect(MobEffectCategory.BENEFICIAL, getColor("#ae2334")));

    public static final RegistryObject<MobEffect> ETHEREAL_GUARD = MOB_EFFECTS.register("ethereal_guard", () ->
            new EtherealGuardEffect(MobEffectCategory.BENEFICIAL, getColor("#6495d0")));

    public static final RegistryObject<MobEffect> VITALITY = MOB_EFFECTS.register("vitality", () ->
            new ElderOakCurseEffect(MobEffectCategory.BENEFICIAL, getColor("#fad64a")));

    public static final RegistryObject<MobEffect> ELDER_OAK_BLESSING = MOB_EFFECTS.register("elder_oak_blessing", () ->
            new ElderOakBlessingEffect(MobEffectCategory.BENEFICIAL, getColor("#ffc766")));

    public static final RegistryObject<MobEffect> ELDER_OAK_CURSE = MOB_EFFECTS.register("elder_oak_curse", () ->
            new ElderOakCurseEffect(MobEffectCategory.HARMFUL, getColor("#884ddb")));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }

    public static int getColor(String hex){
        Color col = Color.decode(hex);
        int result = getIntFromColor(col.getRed(), col.getGreen(), col.getBlue());
        return result;
    }

    public static int getIntFromColor(int Red, int Green, int Blue){

        int R = (Red << 16)  & 0x00FF0000;
        int G = (Green << 8) & 0x0000FF00;
        int B = Blue & 0x000000FF;

        return 0xFF000000 | R | G | B;
    }
}
