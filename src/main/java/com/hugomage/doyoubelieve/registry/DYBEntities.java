package com.hugomage.doyoubelieve.registry;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.entities.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DYBEntities {
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, DoYouBelieve.MOD_ID);

    public static final RegistryObject<EntityType<BigfootEntity>> BIGFOOT = ENTITIES.register("bigfoot", ()->
            EntityType.Builder.of(BigfootEntity::new, EntityClassification.CREATURE)
                    .sized(1.0f,3.3f)
                    .build(new ResourceLocation(DoYouBelieve.MOD_ID, "bigfoot").toString()));
    public static final RegistryObject<EntityType<JerseyDevilEntity>> JERSEY_DEVIL = ENTITIES.register("jersey_devil", ()->
            EntityType.Builder.of(JerseyDevilEntity::new, EntityClassification.MONSTER)
                    .sized(1.0f,3.3f)
                    .build(new ResourceLocation(DoYouBelieve.MOD_ID, "jersey_devil").toString()));
    public static final RegistryObject<EntityType<FresnoEntity>> FRESNO = ENTITIES.register("fresno_nightcrawler", ()->
            EntityType.Builder.of(FresnoEntity::new, EntityClassification.MONSTER)
                    .sized(1.0f,1.5f)
                    .build(new ResourceLocation(DoYouBelieve.MOD_ID, "fresno_nightcrawler").toString()));
    public static final RegistryObject<EntityType<MothmanEntity>> MOTHMAN = ENTITIES.register("mothman", ()->
            EntityType.Builder.of(MothmanEntity::new, EntityClassification.MONSTER)
                    .sized(1.2f,2.5f)
                    .build(new ResourceLocation(DoYouBelieve.MOD_ID, "mothman").toString()));
    public static final RegistryObject<EntityType<MartianEntity>> MARTIAN = ENTITIES.register("martian", ()->
            EntityType.Builder.of(MartianEntity::new, EntityClassification.CREATURE)
                    .sized(0.7f,1.7f)
                    .build(new ResourceLocation(DoYouBelieve.MOD_ID, "martian").toString()));
    public static final RegistryObject<EntityType<RayGunLaserEntity>> RAYGUNLASER =
            ENTITIES.register("raygun_laser",
                    ()->EntityType.Builder.<RayGunLaserEntity>of(RayGunLaserEntity::new, EntityClassification.MISC)
                            .sized(0.25F,0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .fireImmune()
                            .build("raygun_laser"));


}
