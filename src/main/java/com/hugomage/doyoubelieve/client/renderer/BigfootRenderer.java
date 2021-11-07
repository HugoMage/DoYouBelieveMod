package com.hugomage.doyoubelieve.client.renderer;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.client.model.BigfootModel;
import com.hugomage.doyoubelieve.entities.BigfootEntity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class BigfootRenderer extends MobRenderer<BigfootEntity, BigfootModel<BigfootEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(DoYouBelieve.MOD_ID, "textures/entity/bigfoot.png");

    public BigfootRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BigfootModel<>(), 0.8F);
        this.addLayer(new BigfootHeldItemLayer(this));
    }

    protected void setupRotations(BigfootEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        if (!((double)entityLiving.animationSpeedOld < 0.01D)) {
            float f = 13.0F;
            float f1 = entityLiving.animationPosition - entityLiving.animationSpeedOld * (1.0F - partialTicks) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(6.5F * f2));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(BigfootEntity entity) {
        return TEXTURE;
    }
}

