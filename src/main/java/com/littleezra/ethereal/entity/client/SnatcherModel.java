package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Bushhog;
import com.littleezra.ethereal.entity.custom.Snatcher;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SnatcherModel extends AnimatedGeoModel<Snatcher> {
    @Override
    public ResourceLocation getModelResource(Snatcher object) {
        return new ResourceLocation(Ethereal.MODID, "geo/snatcher.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Snatcher object) {
        return new ResourceLocation(Ethereal.MODID, "textures/entity/snatcher.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Snatcher animatable) {
        return new ResourceLocation(Ethereal.MODID, "animations/snatcher.animation.json");
    }
}
