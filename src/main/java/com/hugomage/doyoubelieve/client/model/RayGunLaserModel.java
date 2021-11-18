package com.hugomage.doyoubelieve.client.model;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.entities.RayGunLaserEntity;
import com.hugomage.doyoubelieve.item.RayGunItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;


public class RayGunLaserModel extends AnimatedGeoModel<RayGunLaserEntity> {

    @Override
    public ResourceLocation getModelLocation(RayGunLaserEntity object) {
        return new ResourceLocation(DoYouBelieve.MOD_ID, "geo/raygun_laser.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(RayGunLaserEntity object) {
        return new ResourceLocation(DoYouBelieve.MOD_ID, "textures/entity/raygun_laser.png");

    }

    @Override
    public ResourceLocation getAnimationFileLocation(RayGunLaserEntity animatable) {
        return new ResourceLocation(DoYouBelieve.MOD_ID, "animations/raygun_laser.animations.json");
    }
}