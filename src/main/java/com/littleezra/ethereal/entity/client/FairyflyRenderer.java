package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Fairyfly;
import com.littleezra.ethereal.entity.custom.TotemGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FairyflyRenderer extends GeoEntityRenderer<Fairyfly> {

    public FairyflyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FairyflyModel());
        this.shadowStrength = 0;
    }

    @Override
    public ResourceLocation getTextureLocation(Fairyfly instance) {
        return new ResourceLocation(Ethereal.MODID, "textures/entity/fairyfly.png");
    }

    @Override
    public RenderType getRenderType(Fairyfly animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation)
    {
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }

    @Override
    protected int getBlockLightLevel(Fairyfly fairyfly, BlockPos pos)
    {
        if (fairyfly.getIsSitting()){
            return 15;
        } else{
            return Math.max(super.getBlockLightLevel(fairyfly, pos), 10);
        }
    }
}
