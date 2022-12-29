package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Bushhog;
import com.littleezra.ethereal.entity.custom.Snatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SnatcherRenderer extends GeoEntityRenderer<Snatcher> {

    public SnatcherRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SnatcherModel());
        this.shadowRadius = .6f;
    }

    @Override
    public ResourceLocation getTextureLocation(Snatcher instance) {
        return new ResourceLocation(Ethereal.MODID, "textures/entity/snatcher.png");
    }
}
