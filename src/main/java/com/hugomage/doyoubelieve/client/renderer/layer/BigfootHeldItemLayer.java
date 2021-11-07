package com.hugomage.doyoubelieve.client.renderer.layer;

import com.hugomage.doyoubelieve.client.model.BigfootModel;
import com.hugomage.doyoubelieve.entities.BigfootEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class BigfootHeldItemLayer extends LayerRenderer<BigfootEntity, BigfootModel<BigfootEntity>> {
    public BigfootHeldItemLayer(IEntityRenderer<BigfootEntity, BigfootModel<BigfootEntity>> p_i50938_1_) {
        super(p_i50938_1_);
    }
    private void renderArmWithItem(LivingEntity p_229135_1_, ItemStack p_229135_2_, ItemCameraTransforms.TransformType p_229135_3_, HandSide p_229135_4_, MatrixStack p_229135_5_, IRenderTypeBuffer p_229135_6_, int p_229135_7_) {
        if (!p_229135_2_.isEmpty()) {
            p_229135_5_.pushPose();
            this.getParentModel().translateToHand(p_229135_4_, p_229135_5_);
            p_229135_5_.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            p_229135_5_.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            boolean flag = p_229135_4_ == HandSide.LEFT;
            p_229135_5_.translate((double)((float)(flag ? -1 : 1) / 16.0F), 0.125D, -0.625D);
            Minecraft.getInstance().getItemInHandRenderer().renderItem(p_229135_1_, p_229135_2_, p_229135_3_, flag, p_229135_5_, p_229135_6_, p_229135_7_);
            p_229135_5_.popPose();
        }
    }
    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, BigfootEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        ItemStack itemstack = p_225628_4_.getItemBySlot(EquipmentSlotType.MAINHAND);
        ModelRenderer modelrenderer = this.getParentModel().getFlowerHoldingArm();
        modelrenderer.translateAndRotate(p_225628_1_);
            float f = -1.6F;
            float f1 = 1.4F;

            if (p_225628_4_.isEating()) {
                f -= 0.2F * MathHelper.sin(p_225628_8_ * 0.6F) + 0.2F;
                f1 -= 0.09F * MathHelper.sin(p_225628_8_ * 0.6F);
            }

            p_225628_1_.pushPose();
            p_225628_1_.translate(0.1F, 0.7F, -0.2F);
            Minecraft.getInstance().getItemInHandRenderer().renderItem(p_225628_4_, itemstack, ItemCameraTransforms.TransformType.GROUND, false, p_225628_1_, p_225628_2_, p_225628_3_);
            p_225628_1_.popPose();
        }
    }


