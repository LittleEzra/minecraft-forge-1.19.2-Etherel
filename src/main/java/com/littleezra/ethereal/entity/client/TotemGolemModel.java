package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Cnitherea;
import com.littleezra.ethereal.entity.custom.TotemGolem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TotemGolemModel extends AnimatedGeoModel<TotemGolem> {
    @Override
    public ResourceLocation getModelResource(TotemGolem object) {
        return new ResourceLocation(Ethereal.MODID, "geo/totem_golem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TotemGolem object) {
        return new ResourceLocation(Ethereal.MODID, "textures/entity/totem_golem.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TotemGolem animatable) {
        return new ResourceLocation(Ethereal.MODID, "animations/totem_golem.animation.json");
    }
}
