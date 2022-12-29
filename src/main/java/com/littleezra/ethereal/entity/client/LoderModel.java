package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Loder;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LoderModel extends AnimatedGeoModel<Loder> {
    @Override
    public ResourceLocation getModelResource(Loder object) {
        return new ResourceLocation(Ethereal.MODID, "geo/loder.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Loder object) {
        if (object.isMoving()){
            switch (object.tickCount % 4){
                default -> { return new ResourceLocation(Ethereal.MODID, "textures/entity/loder_0.png"); }
                case 1 -> { return new ResourceLocation(Ethereal.MODID, "textures/entity/loder_1.png"); }
                case 2 -> { return new ResourceLocation(Ethereal.MODID, "textures/entity/loder_2.png"); }
                case 3 -> { return new ResourceLocation(Ethereal.MODID, "textures/entity/loder_3.png"); }
            }
        } else{
            return new ResourceLocation(Ethereal.MODID, "textures/entity/loder_0.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(Loder animatable) {
        return new ResourceLocation(Ethereal.MODID, "animations/loder.animation.json");
    }
}
