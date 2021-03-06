package com.hugomage.doyoubelieve.client.renderer;

import com.hugomage.doyoubelieve.client.model.RayGunLaserModel;
import com.hugomage.doyoubelieve.common.entities.RayGunLaserEntity;
import com.hugomage.doyoubelieve.common.item.RayGunItem;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class RayGunLaserRenderer extends GeoProjectilesRenderer<RayGunLaserEntity> {

    public RayGunLaserRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new RayGunLaserModel());
    }

}

