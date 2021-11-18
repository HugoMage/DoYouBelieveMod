package com.hugomage.doyoubelieve.client.renderer;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.client.model.BigfootModel;
import com.hugomage.doyoubelieve.client.model.MartianModel;
import com.hugomage.doyoubelieve.entities.BigfootEntity;
import com.hugomage.doyoubelieve.entities.MartianEntity;
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

public class MartianRenderer extends GeoEntityRenderer<MartianEntity> {
    protected MartianEntity entity;
    public MartianRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new MartianModel());
    }

    @Override
    public void renderEarly(MartianEntity entity, MatrixStack stackIn, float ticks, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        this.entity = entity;
        super.renderEarly(entity, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("item")) {
            stack.pushPose();
            stack.translate(0.26D, 0.45D, 0.1D);
            stack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            stack.scale(0.7f, 0.7f, 0.7f);
            Minecraft.getInstance().getItemRenderer().renderStatic(entity.getItemBySlot(EquipmentSlotType.MAINHAND), ItemCameraTransforms.TransformType.GROUND, packedLightIn, packedOverlayIn, stack, this.rtb);
            stack.popPose();
            bufferIn = rtb.getBuffer(RenderType.entityTranslucent(whTexture));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

}

