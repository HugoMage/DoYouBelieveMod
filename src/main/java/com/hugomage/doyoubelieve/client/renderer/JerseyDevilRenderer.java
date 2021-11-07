package com.hugomage.doyoubelieve.client.renderer;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.client.model.BigfootModel;
import com.hugomage.doyoubelieve.client.model.JerseyDevilModel;
import com.hugomage.doyoubelieve.entities.BigfootEntity;
import com.hugomage.doyoubelieve.entities.JerseyDevilEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class JerseyDevilRenderer extends MobRenderer<JerseyDevilEntity, JerseyDevilModel<JerseyDevilEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(DoYouBelieve.MOD_ID, "textures/entity/jersey_devil.png");

    public JerseyDevilRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new JerseyDevilModel<>(), 0.8F);
    }

    @Override
    public ResourceLocation getTextureLocation(JerseyDevilEntity entity) {
        return TEXTURE;
    }
}

