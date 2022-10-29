package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Cnitherea;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CnithereaRenderer extends GeoEntityRenderer<Cnitherea> {
    public CnithereaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CnithereaModel());
        this.shadowStrength = 0f;
    }

    @Override
    public ResourceLocation getTextureLocation(Cnitherea instance) {
        return new ResourceLocation(Ethereal.MODID, "textures/entity/cnitherea.png");
    }

    @Override
    public RenderType getRenderType(Cnitherea animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation)
    {

        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
