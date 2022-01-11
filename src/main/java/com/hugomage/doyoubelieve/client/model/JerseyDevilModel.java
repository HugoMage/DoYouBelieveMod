package com.hugomage.doyoubelieve.client.model;

import com.google.common.collect.ImmutableList;
import com.hugomage.doyoubelieve.common.entities.JerseyDevilEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

import java.util.Collections;

public class JerseyDevilModel<T extends JerseyDevilEntity> extends AgeableModel<T> {
    private final ModelRenderer body;
    private final ModelRenderer lwing;
    private final ModelRenderer lowerwing2;
    private final ModelRenderer rwing;
    private final ModelRenderer lowerwing;
    private final ModelRenderer head;
    private final ModelRenderer cube_r1;
    private final ModelRenderer tail;

    public JerseyDevilModel() {
        texWidth = 256;
        texHeight = 256;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 24.0F, 0.0F);
        body.texOffs(14, 109).addBox(-1.0F, -45.0F, 7.0F, 2.0F, 21.0F, 2.0F, 0.0F, false);
        body.texOffs(83, 88).addBox(-6.0F, -24.0F, -4.0F, 12.0F, 11.0F, 11.0F, 0.0F, false);
        body.texOffs(54, 43).addBox(-7.0F, -43.0F, -13.0F, 14.0F, 19.0F, 20.0F, 0.0F, false);
        body.texOffs(20, 89).addBox(-1.0F, -45.0F, -13.0F, 2.0F, 2.0F, 20.0F, 0.0F, false);
        body.texOffs(54, 0).addBox(-7.0F, -43.0F, -13.0F, 14.0F, 23.0F, 20.0F, 0.5F, false);
        body.texOffs(62, 0).addBox(-6.0F, -13.0F, 0.0F, 3.0F, 13.0F, 3.0F, 0.0F, false);
        body.texOffs(62, 0).addBox(-6.0F, -24.0F, -12.0F, 3.0F, 13.0F, 3.0F, 0.0F, false);
        body.texOffs(62, 0).addBox(3.0F, -24.0F, -12.0F, 3.0F, 13.0F, 3.0F, 0.0F, false);
        body.texOffs(62, 0).addBox(3.0F, -13.0F, 0.0F, 3.0F, 13.0F, 3.0F, 0.0F, false);

        lwing = new ModelRenderer(this);
        lwing.setPos(-5.0F, -42.0F, -9.0F);
        body.addChild(lwing);
        lwing.texOffs(0, 31).addBox(0.5F, -51.0F, -2.0F, 0.0F, 31.0F, 27.0F, 0.0F, false);

        lowerwing2 = new ModelRenderer(this);
        lowerwing2.setPos(0.5F, -1.25F, -0.75F);
        lwing.addChild(lowerwing2);
        lowerwing2.texOffs(88, 110).addBox(-1.5F, -18.75F, -1.25F, 3.0F, 19.0F, 4.0F, 0.0F, false);
        lowerwing2.texOffs(6, 72).addBox(0.0F, -18.75F, 2.75F, 0.0F, 16.0F, 17.0F, 0.0F, false);

        rwing = new ModelRenderer(this);
        rwing.setPos(5.0F, -42.0F, -9.0F);
        body.addChild(rwing);
        rwing.texOffs(0, 31).addBox(-0.5F, -51.0F, -2.0F, 0.0F, 31.0F, 27.0F, 0.0F, false);

        lowerwing = new ModelRenderer(this);
        lowerwing.setPos(-0.5F, -1.25F, -0.75F);
        rwing.addChild(lowerwing);
        lowerwing.texOffs(6, 72).addBox(0.0F, -18.75F, 2.75F, 0.0F, 16.0F, 17.0F, 0.0F, false);
        lowerwing.texOffs(88, 110).addBox(-1.5F, -18.75F, -1.25F, 3.0F, 19.0F, 4.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setPos(0.0F, -35.0F, -13.0F);
        body.addChild(head);
        head.texOffs(47, 0).addBox(0.4F, -11.0F, -16.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
        head.texOffs(0, 3).addBox(-5.6F, -4.0F, -16.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(0, 0).addBox(2.4F, -4.0F, -16.0F, 3.0F, 2.0F, 1.0F, 0.0F, false);
        head.texOffs(44, 93).addBox(-2.4F, -11.0F, -14.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        head.texOffs(44, 93).addBox(0.4F, -11.0F, -14.0F, 2.0F, 2.0F, 6.0F, 0.0F, false);
        head.texOffs(47, 1).addBox(-2.4F, -11.0F, -16.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
        head.texOffs(0, 1).addBox(0.0F, 5.0F, -20.0F, 0.0F, 9.0F, 5.0F, 0.0F, false);
        head.texOffs(48, 99).addBox(-2.0F, -3.0F, -16.0F, 4.0F, 6.0F, 16.0F, 0.0F, false);
        head.texOffs(102, 43).addBox(-1.0F, -4.0F, -14.0F, 2.0F, 1.0F, 14.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(0.0F, 0.0F, -20.5F);
        head.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.9163F, 0.0F, 0.0F);
        cube_r1.texOffs(102, 0).addBox(-2.5F, 1.0F, -4.5F, 5.0F, 7.0F, 11.0F, 0.0F, false);

        tail = new ModelRenderer(this);
        tail.setPos(0.0F, -15.5F, 5.75F);
        body.addChild(tail);
        tail.texOffs(0, 0).addBox(-2.0F, -1.5F, 1.25F, 4.0F, 4.0F, 20.0F, 0.0F, false);
        tail.texOffs(28, 0).addBox(-1.0F, -1.5F, 21.25F, 2.0F, 2.0F, 15.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = ((float)(entityIn.getId() * 3) + ageInTicks) * 0.13F;
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.lwing.zRot = MathHelper.cos(f) * -16.0F * ((float)Math.PI / 180F);
        this.rwing.zRot = MathHelper.cos(f) * 16.0F * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return ImmutableList.of(body);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}