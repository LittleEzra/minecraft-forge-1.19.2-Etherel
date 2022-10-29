package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Cnitherea;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CnithereaModel extends AnimatedGeoModel<Cnitherea> {

    @Override
    public ResourceLocation getModelResource(Cnitherea object) {
        return new ResourceLocation(Ethereal.MODID, "geo/cnitherea.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Cnitherea object) {
        return new ResourceLocation(Ethereal.MODID, "textures/entity/cnitherea.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Cnitherea animatable) {
        return new ResourceLocation(Ethereal.MODID, "animations/cnitherea.animation.json");
    }
}
