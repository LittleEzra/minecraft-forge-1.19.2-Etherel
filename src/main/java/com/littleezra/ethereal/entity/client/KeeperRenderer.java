package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Keeper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class KeeperRenderer extends GeoEntityRenderer<Keeper> {
    public KeeperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new KeeperModel());
    }

    @Override
    public ResourceLocation getTextureLocation(Keeper instance) {
        return new ResourceLocation(Ethereal.MODID, "textures/entity/keeper.png");
    }
}
