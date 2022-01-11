package com.hugomage.doyoubelieve.client.renderer;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.client.model.BigfootModel;
import com.hugomage.doyoubelieve.common.entities.BigfootEntity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BigfootRenderer extends GeoEntityRenderer<BigfootEntity> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(DoYouBelieve.MOD_ID, "textures/entity/bigfoot.png");
    protected BigfootEntity entity;
    public BigfootRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new BigfootModel());
    }

    @Override
    public void renderEarly(BigfootEntity animatable, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        this.entity = entity;
    }


    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("Item")) {
            stack.pushPose();
            stack.translate(0.1F, 0.7F, -0.2F);
            stack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
            Minecraft.getInstance().getItemRenderer().renderStatic(entity.getItemBySlot(EquipmentSlotType.MAINHAND), ItemCameraTransforms.TransformType.GROUND, packedLightIn, packedOverlayIn, stack, this.rtb);
            stack.popPose();
            bufferIn = rtb.getBuffer(RenderType.entityTranslucent(whTexture));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

}

