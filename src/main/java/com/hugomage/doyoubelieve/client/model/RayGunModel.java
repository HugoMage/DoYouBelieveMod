package com.hugomage.doyoubelieve.client.model;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.entities.BigfootEntity;
import com.hugomage.doyoubelieve.item.RayGunItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class RayGunModel extends AnimatedGeoModel<RayGunItem> {

    @Override
    public ResourceLocation getModelLocation(RayGunItem object) {
        return new ResourceLocation(DoYouBelieve.MOD_ID, "geo/raygun.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(RayGunItem object) {
        return new ResourceLocation(DoYouBelieve.MOD_ID, "textures/item/raygun.png");

    }

    @Override
    public ResourceLocation getAnimationFileLocation(RayGunItem animatable) {
        return new ResourceLocation(DoYouBelieve.MOD_ID, "animations/raygun.animations.json");
    }
}