package com.hugomage.doyoubelieve.client.renderer;

import com.hugomage.doyoubelieve.client.model.RayGunModel;
import com.hugomage.doyoubelieve.item.RayGunItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RayGunRenderer extends GeoItemRenderer<RayGunItem> {

    public RayGunRenderer() {
        super(new RayGunModel());
    }

}

