package com.hugomage.doyoubelieve.client.model;

import com.hugomage.doyoubelieve.entities.JerseyDevilEntity;
import com.hugomage.doyoubelieve.entities.MothmanEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class MothmanModel<T extends MothmanEntity> extends AgeableModel<T> {
    private final ModelRenderer mothman;
    private final ModelRenderer wing2;
    private final ModelRenderer smallwingl;
    private final ModelRenderer wing;
    private final ModelRenderer smallwingr;
    private final ModelRenderer lleg;
    private final ModelRenderer rleg;

    public MothmanModel() {
        texWidth = 128;
        texHeight = 128;

        mothman = new ModelRenderer(this);
        mothman.setPos(5.0F, 12.0F, 2.0F);
        mothman.texOffs(0, 0).addBox(-16.0F, -27.0F, -10.0F, 21.0F, 17.0F, 17.0F, 0.0F, false);
        mothman.texOffs(76, 0).addBox(-11.0F, -29.0F, -13.0F, 11.0F, 12.0F, 12.0F, 0.0F, false);
        mothman.texOffs(57, 68).addBox(-14.0F, -26.0F, -9.0F, 17.0F, 15.0F, 13.0F, 0.0F, false);
        mothman.texOffs(70, 24).addBox(-13.0F, -26.0F, -5.0F, 15.0F, 26.0F, 11.0F, 0.0F, false);

        wing2 = new ModelRenderer(this);
        wing2.setPos(4.25F, -24.5F, -2.0F);
        mothman.addChild(wing2);
        wing2.texOffs(0, 35).addBox(-0.25F, -1.5F, -1.0F, 33.0F, 21.0F, 2.0F, 0.0F, false);

        smallwingl = new ModelRenderer(this);
        smallwingl.setPos(32.75F, -0.5F, 0.0F);
        wing2.addChild(smallwingl);
        smallwingl.texOffs(0, 81).addBox(0.0F, -1.0F, -0.5F, 22.0F, 13.0F, 1.0F, 0.0F, false);

        wing = new ModelRenderer(this);
        wing.setPos(-15.25F, -23.5F, -2.0F);
        mothman.addChild(wing);
        wing.texOffs(0, 35).addBox(-32.75F, -1.5F, -1.0F, 33.0F, 21.0F, 2.0F, 0.0F, true);

        smallwingr = new ModelRenderer(this);
        smallwingr.setPos(-32.75F, 0.5F, 0.0F);
        wing.addChild(smallwingr);
        smallwingr.texOffs(0, 81).addBox(-22.0F, -2.0F, -0.5F, 22.0F, 13.0F, 1.0F, 0.0F, true);

        lleg = new ModelRenderer(this);
        lleg.setPos(0.0F, -1.0F, 0.0F);
        mothman.addChild(lleg);
        lleg.texOffs(0, 0).addBox(-2.0F, 1.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, true);

        rleg = new ModelRenderer(this);
        rleg.setPos(-11.0F, -1.0F, -2.0F);
        mothman.addChild(rleg);
        rleg.texOffs(0, 0).addBox(-2.0F, 1.0F, 0.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn.isFlying()) {
            this.wing.yRot = MathHelper.cos(0.75F * ageInTicks);
            this.wing2.yRot = -MathHelper.cos(0.75F * ageInTicks);
            this.smallwingr.yRot = MathHelper.cos(0.75F * ageInTicks);
            this.smallwingl.yRot = -MathHelper.cos(0.75F * ageInTicks);
        } else
        this.rleg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.lleg.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
    }
    @Override
    public void renderToBuffer(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        mothman.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return null;
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        return null;
    }


    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}