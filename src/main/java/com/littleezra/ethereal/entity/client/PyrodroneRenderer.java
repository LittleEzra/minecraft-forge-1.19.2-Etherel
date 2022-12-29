package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Pyrodrone;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.layer.LayerGlowingAreasGeo;

import java.util.function.Function;

public class PyrodroneRenderer extends GeoEntityRenderer<Pyrodrone> {

    public PyrodroneRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new PyrodroneModel());
        this.shadowStrength = 0f;
        /*this.addLayer(new LayerGlowingAreasGeo<>(this,
                this::getTextureLocation,
                (instance) -> getGeoModelProvider().getModelResource(instance),
                RenderType::eyes));*/
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Pyrodrone instance) {
        ResourceLocation location = new ResourceLocation(Ethereal.MODID, "textures/entity/pyrodrone_0.png");
        if (instance.tickCount % 2 == 0){
            location = new ResourceLocation(Ethereal.MODID, "textures/entity/pyrodrone_1.png");
        }

        return location;
    }

    @Override
    public RenderType getRenderType(Pyrodrone animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
