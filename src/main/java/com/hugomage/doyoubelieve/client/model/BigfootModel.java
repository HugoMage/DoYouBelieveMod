package com.hugomage.doyoubelieve.client.model;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.entities.BigfootEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class BigfootModel extends AnimatedGeoModel<BigfootEntity> {

    @Override
    public ResourceLocation getModelLocation(BigfootEntity object) {
        return new ResourceLocation(DoYouBelieve.MOD_ID, "geo/bigfoot.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(BigfootEntity object) {
        return new ResourceLocation(DoYouBelieve.MOD_ID, "textures/entity/bigfoot.png");

    }

    @Override
    public ResourceLocation getAnimationFileLocation(BigfootEntity animatable) {
        return new ResourceLocation(DoYouBelieve.MOD_ID, "animations/bigfoot.animations.json");
    }
}