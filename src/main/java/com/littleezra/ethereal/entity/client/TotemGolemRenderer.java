package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Cnitherea;
import com.littleezra.ethereal.entity.custom.TotemGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TotemGolemRenderer extends GeoEntityRenderer<TotemGolem> {

    public TotemGolemRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TotemGolemModel());
        this.shadowStrength = 0.5f;
    }

    @Override
    public ResourceLocation getTextureLocation(TotemGolem instance) {
        return new ResourceLocation(Ethereal.MODID, "textures/entity/totem_golem.png");
    }

    @Override
    public RenderType getRenderType(TotemGolem animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation)
    {
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
