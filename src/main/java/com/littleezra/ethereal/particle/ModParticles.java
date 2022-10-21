package com.littleezra.ethereal.particle;

import com.littleezra.ethereal.Ethereal;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Ethereal.MODID);

    // Adding Particles
    public static final RegistryObject<SimpleParticleType> ETHEREAL_FLAME_PARTICLES = PARTICLE_TYPES.register("ethereal_flame_particles",
            () -> new SimpleParticleType(true));


    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
