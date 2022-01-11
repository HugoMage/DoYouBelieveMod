package com.hugomage.doyoubelieve.client.renderer;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.client.model.FresnoModel;
import com.hugomage.doyoubelieve.common.entities.BigfootEntity;
import com.hugomage.doyoubelieve.common.entities.FresnoEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class FresnoRenderer extends MobRenderer<FresnoEntity, FresnoModel<FresnoEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(DoYouBelieve.MOD_ID, "textures/entity/fresno_nightcrawler.png");

    public FresnoRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new FresnoModel<>(), 0.8F);
    }

    protected void setupRotations(FresnoEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
        if (!((double)entityLiving.animationSpeedOld < 0.01D)) {
            float f = 13.0F;
            float f1 = entityLiving.animationPosition - entityLiving.animationSpeedOld * (1.0F - partialTicks) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(6.5F * f2));
        }
    }

    @Override
    public ResourceLocation getTextureLocation(FresnoEntity entity) {
        return TEXTURE;
    }
}

