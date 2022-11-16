package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Bushhog;
import com.littleezra.ethereal.entity.custom.Fairyfly;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BushhogModel extends AnimatedGeoModel<Bushhog> {
    @Override
    public ResourceLocation getModelResource(Bushhog object) {
        return new ResourceLocation(Ethereal.MODID, "geo/bushhog.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Bushhog object) {
        return new ResourceLocation(Ethereal.MODID, "textures/entity/bushhog.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Bushhog animatable) {
        return new ResourceLocation(Ethereal.MODID, "animations/bushhog.animation.json");
    }
}
