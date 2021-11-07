package com.hugomage.doyoubelieve.client.model;

import com.hugomage.doyoubelieve.entities.BigfootEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class BigfootModel<T extends BigfootEntity> extends AgeableModel<T>  implements IHasArm {
    private final ModelRenderer bb_main;
    private final ModelRenderer body;
    public final ModelRenderer head;
    private final ModelRenderer rarm;
    private final ModelRenderer rleg;
    private final ModelRenderer lleg;
    private final ModelRenderer larm;

    public BigfootModel() {
        texWidth = 128;
        texHeight = 128;

        bb_main = new ModelRenderer(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);


        body = new ModelRenderer(this);
        body.setPos(0.0F, -35.25F, 2.5F);
        bb_main.addChild(body);
        body.texOffs(0, 0).addBox(-9.0F, -12.75F, -6.5F, 18.0F, 13.0F, 12.0F, 0.0F, false);
        body.texOffs(60, 0).addBox(-6.0F, 0.25F, -3.5F, 12.0F, 12.0F, 8.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(0.0F, -11.9167F, -3.3333F);
        body.addChild(head);
        head.texOffs(53, 57).addBox(-4.0F, -5.8333F, -7.1667F, 8.0F, 13.0F, 11.0F, 0.0F, false);
        head.texOffs(64, 48).addBox(-4.0F, 7.1667F, -7.1667F, 8.0F, 1.0F, 4.0F, 0.0F, false);
        head.texOffs(24, 25).addBox(-3.0F, 1.1667F, -9.1667F, 6.0F, 5.0F, 2.0F, 0.0F, false);

        rarm = new ModelRenderer(this);
        rarm.setPos(9.0F, -5.25F, -1.5F);
        body.addChild(rarm);
        rarm.texOffs(0, 25).addBox(0.0F, -4.5F, -4.0F, 8.0F, 35.0F, 8.0F, 0.0F, true);

        rleg = new ModelRenderer(this);
        rleg.setPos(3.5F, 12.25F, 0.0F);
        body.addChild(rleg);
        rleg.texOffs(64, 20).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 20.0F, 5.0F, 0.0F, true);
        rleg.texOffs(74, 35).addBox(-2.5F, 20.0F, -7.5F, 5.0F, 3.0F, 10.0F, 0.0F, false);

        lleg = new ModelRenderer(this);
        lleg.setPos(-3.5F, 12.25F, 0.0F);
        body.addChild(lleg);
        lleg.texOffs(74, 35).addBox(-2.5F, 20.0F, -7.5F, 5.0F, 3.0F, 10.0F, 0.0F, false);
        lleg.texOffs(64, 20).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 20.0F, 5.0F, 0.0F, false);

        larm = new ModelRenderer(this);
        larm.setPos(-9.0F, -5.25F, -1.5F);
        body.addChild(larm);
        larm.texOffs(0, 25).addBox(-8.0F, -4.5F, -4.0F, 8.0F, 35.0F, 8.0F, 0.0F, false);
    }


    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.rleg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.lleg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.rarm.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.larm.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        int i = entityIn.getAttackAnimationRemainingTicks();
        int i1 = entityIn.getEatAnimation();
        if (i > 0) {
            float p_212843_4_ = 0;
            this.rarm.xRot = -2.0F + 1.5F * MathHelper.triangleWave((float) i - p_212843_4_, 10.0F);
            this.larm.xRot = -2.0F + 1.5F * MathHelper.triangleWave((float) i - p_212843_4_, 10.0F);
        }
        if (i1 > 0) {
            float p_225597_4_ = 0;
            this.head.xRot = ((float)Math.PI / 2F) + 0.2F * MathHelper.sin(p_225597_4_ * 0.6F);
            this.larm.xRot = -0.4F - 0.2F * MathHelper.sin(p_225597_4_ * 0.6F);

        }
    }
    @Override
    public void translateToHand(HandSide sideIn, MatrixStack matrixStackIn) {
        this.getArm(sideIn).translateAndRotate(matrixStackIn);
    }
    public ModelRenderer getArm(HandSide p_1912161)
    {
        return p_1912161 == HandSide.LEFT ? this.larm : this.rarm;
    }
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        bb_main.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return null;
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return null;
    }

    public ModelRenderer getFlowerHoldingArm() {
        return this.larm;
    }
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}