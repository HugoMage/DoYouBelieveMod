package com.hugomage.doyoubelieve.client.renderer;

import com.hugomage.doyoubelieve.client.model.MothmanModel;
import com.hugomage.doyoubelieve.entities.MothmanEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
@OnlyIn(Dist.CLIENT)
public class MothmanEyeLayer<T extends MothmanEntity, M extends MothmanModel<T>> extends AbstractEyesLayer<T, M>{
        private static final RenderType MOTHMAN_EYES = RenderType.eyes(new ResourceLocation("textures/entity/mothman_eyes.png"));
        public MothmanEyeLayer(IEntityRenderer<T, M> p_i50921_1_) {
            super(p_i50921_1_);
        }

        public RenderType renderType() {
            return MOTHMAN_EYES;
        }

}
