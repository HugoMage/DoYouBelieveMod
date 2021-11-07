package com.hugomage.doyoubelieve.client.model;

import com.google.common.collect.ImmutableList;
import com.hugomage.doyoubelieve.entities.FresnoEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

import java.util.Collections;

public class FresnoModel<T extends FresnoEntity> extends AgeableModel<T> {
    private final ModelRenderer creature;
    private final ModelRenderer body;
    private final ModelRenderer left_leg;
    private final ModelRenderer right_leg;

    public FresnoModel() {
        texWidth = 64;
        texHeight = 64;

        creature = new ModelRenderer(this);
        creature.setPos(0.0F, -1.0F, 0.0F);

        body = new ModelRenderer(this);
        body.setPos(0.0F, 7.0F, 0.0F);
        creature.addChild(body);
        body.texOffs(0, 0).addBox(-6.0F, -15.0F, -5.0F, 12.0F, 15.0F, 9.0F, 0.0F, false);

        left_leg = new ModelRenderer(this);
        left_leg.setPos(4.0F, 7.0F, -1.0F);
        creature.addChild(left_leg);
        left_leg.texOffs(0, 25).addBox(-1.0F, 0.0F, -1.0F, 3.0F, 18.0F, 3.0F, 0.0F, false);

        right_leg = new ModelRenderer(this);
        right_leg.setPos(-5.0F, 7.0F, -1.0F);
        creature.addChild(right_leg);
        right_leg.texOffs(13, 25).addBox(-1.0F, 0.0F, -1.0F, 3.0F, 18.0F, 3.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.right_leg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.left_leg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;

    }
    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        creature.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(creature);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}