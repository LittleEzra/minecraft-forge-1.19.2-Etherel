package com.littleezra.ethereal.entity.client;

import com.littleezra.ethereal.Ethereal;
import com.littleezra.ethereal.entity.custom.Cnitherea;
import com.littleezra.ethereal.entity.custom.Loder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LoderRenderer extends GeoEntityRenderer<Loder> {
    public LoderRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LoderModel());
        this.shadowStrength = 0f;
    }

    @Override
    public ResourceLocation getTextureLocation(Loder instance) {
        if (instance.isMoving()){
            switch (instance.tickCount % 4){
                default -> { return new ResourceLocation(Ethereal.MODID, "textures/entity/loder_0.png"); }
                case 1 -> { return new ResourceLocation(Ethereal.MODID, "textures/entity/loder_1.png"); }
                case 2 -> { return new ResourceLocation(Ethereal.MODID, "textures/entity/loder_2.png"); }
                case 3 -> { return new ResourceLocation(Ethereal.MODID, "textures/entity/loder_3.png"); }
            }
        } else{
            return new ResourceLocation(Ethereal.MODID, "textures/entity/loder_0.png");
        }
    }
}
