package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Pyrodrone;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PyrodroneModel extends AnimatedGeoModel<Pyrodrone> {
    @Override
    public ResourceLocation getModelResource(Pyrodrone object) {
        return new ResourceLocation(Ethereal.MODID, "geo/pyrodrone.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Pyrodrone object) {
        ResourceLocation location = new ResourceLocation(Ethereal.MODID, "textures/entity/pyrodrone_0.png");
        if (object.tickCount % 2 == 0){
            location = new ResourceLocation(Ethereal.MODID, "textures/entity/pyrodrone_1.png");
        }

        return location;
    }

    @Override
    public ResourceLocation getAnimationResource(Pyrodrone animatable) {
        return new ResourceLocation(Ethereal.MODID, "animations/pyrodrone.animation.json");
    }
}
