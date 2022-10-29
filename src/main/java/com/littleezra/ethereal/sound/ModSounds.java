package com.littleezra.ethereal.sound;

import com.littleezra.ethereal.Ethereal;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Ethereal.MODID);

    public static final RegistryObject<SoundEvent> MUSIC_DISC_AETHER = registerSoundEvent("music_disc.aether");

    public static final RegistryObject<SoundEvent> ETHEREAL_SPIKE_ACTIVATE = registerSoundEvent("block.ethereal_spike.activate");
    public static final RegistryObject<SoundEvent> ETHEREAL_SPIKE_RESET = registerSoundEvent("block.ethereal_spike.reset");

    public static final RegistryObject<SoundEvent> ETHEREAL_JELLYFISH_MOVE = registerSoundEvent("entity.ethereal_jellyfish.move");

    // Ethereal Sap Block sounds

    public static final RegistryObject<SoundEvent> ETHEREAL_SAP_BREAK = registerSoundEvent("block.ethereal_sap.break");
    public static final RegistryObject<SoundEvent> ETHEREAL_SAP_STEP =  registerSoundEvent("block.ethereal_sap.step");
    public static final RegistryObject<SoundEvent> ETHEREAL_SAP_PLACE = registerSoundEvent("block.ethereal_sap.place");
    public static final RegistryObject<SoundEvent> ETHEREAL_SAP_HIT =   registerSoundEvent("block.ethereal_sap.hit");
    public static final RegistryObject<SoundEvent> ETHEREAL_SAP_FALL =  registerSoundEvent("block.ethereal_sap.fall");

    public static final ForgeSoundType ETHEREAL_SAP_BLOCK = new ForgeSoundType(1f, 1f,
            ModSounds.ETHEREAL_SAP_BREAK,
            ModSounds.ETHEREAL_SAP_STEP,
            ModSounds.ETHEREAL_SAP_PLACE,
            ModSounds.ETHEREAL_SAP_HIT,
            ModSounds.ETHEREAL_SAP_FALL
    );

    public static final RegistryObject<SoundEvent> ETHEREAL_PLATING_BREAK = registerSoundEvent("block.ethereal_plating.break");
    public static final RegistryObject<SoundEvent> ETHEREAL_PLATING_STEP =  registerSoundEvent("block.ethereal_plating.step");
    public static final RegistryObject<SoundEvent> ETHEREAL_PLATING_PLACE = registerSoundEvent("block.ethereal_plating.place");
    public static final RegistryObject<SoundEvent> ETHEREAL_PLATING_HIT =   registerSoundEvent("block.ethereal_plating.hit");
    public static final RegistryObject<SoundEvent> ETHEREAL_PLATING_FALL =  registerSoundEvent("block.ethereal_plating.fall");

    public static final ForgeSoundType ETHEREAL_PLATING = new ForgeSoundType(1f, 1f,
            ModSounds.ETHEREAL_PLATING_BREAK,
            ModSounds.ETHEREAL_PLATING_STEP,
            ModSounds.ETHEREAL_PLATING_PLACE,
            ModSounds.ETHEREAL_PLATING_HIT,
            ModSounds.ETHEREAL_PLATING_FALL
    );

    public static void register(IEventBus eventBus)
    {
        SOUND_EVENTS.register(eventBus);
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name)
    {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(Ethereal.MODID, name)));
    }
}
