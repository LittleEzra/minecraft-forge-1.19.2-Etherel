package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Bushhog;
import com.littleezra.ethereal.entity.custom.Fairyfly;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BushhogRenderer extends GeoEntityRenderer<Bushhog> {

    public BushhogRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BushhogModel());
        this.shadowRadius = 1;
    }

    @Override
    public ResourceLocation getTextureLocation(Bushhog instance) {
        return new ResourceLocation(Ethereal.MODID, "textures/entity/bushhog.png");
    }
}
