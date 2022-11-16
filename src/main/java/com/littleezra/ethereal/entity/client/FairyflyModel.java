package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Fairyfly;
import com.littleezra.ethereal.entity.custom.TotemGolem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FairyflyModel extends AnimatedGeoModel<Fairyfly> {
    @Override
    public ResourceLocation getModelResource(Fairyfly object) {
        return new ResourceLocation(Ethereal.MODID, "geo/fairyfly.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Fairyfly object) {
        return new ResourceLocation(Ethereal.MODID, "textures/entity/fairyfly.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Fairyfly animatable) {
        return new ResourceLocation(Ethereal.MODID, "animations/fairyfly.animation.json");
    }
}
