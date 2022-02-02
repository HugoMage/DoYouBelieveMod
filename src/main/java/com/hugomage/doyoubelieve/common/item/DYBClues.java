package com.hugomage.doyoubelieve.common.item;

import com.hugomage.doyoubelieve.common.utils.Area;
import com.hugomage.doyoubelieve.registry.DYBEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public enum DYBClues {
    BIGFOOT("bigfoot", new Area(0, 203, 18, 23), DYBEntities.BIGFOOT);

    private final String id;
    private final Area textureInformation;
    private final RegistryObject<? extends EntityType<?>> entity;

    DYBClues(String id, Area textureInformation, RegistryObject<? extends EntityType<? extends Entity>> entity) {
        this.id = id;
        this.textureInformation = textureInformation;
        this.entity = entity;
    }

    public String getId() {
        return id;
    }

    public Area getTextureInformation() {
        return textureInformation;
    }

    public RegistryObject<? extends EntityType<?>> getEntity() {
        return entity;
    }
}
