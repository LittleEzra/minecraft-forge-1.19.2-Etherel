package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Keeper;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class KeeperModel extends AnimatedGeoModel<Keeper> {
    @Override
    public ResourceLocation getModelResource(Keeper object) {
        return new ResourceLocation(Ethereal.MODID, "geo/keeper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Keeper object) {
        return new ResourceLocation(Ethereal.MODID, "textures/entity/keeper.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Keeper object) {
        return new ResourceLocation(Ethereal.MODID, "animations/keeper.animation.json");
    }
}
