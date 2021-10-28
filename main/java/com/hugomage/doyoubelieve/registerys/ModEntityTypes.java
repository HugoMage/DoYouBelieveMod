package com.hugomage.doyoubelieve.registerys;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.entities.BigfootEntity;
import com.hugomage.doyoubelieve.entities.FresnoEntity;
import com.hugomage.doyoubelieve.entities.JerseyDevilEntity;
import com.hugomage.doyoubelieve.entities.MothmanEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, DoYouBelieve.MOD_ID);

    public static final RegistryObject<EntityType<BigfootEntity>> BIGFOOT = ENTITY_TYPES.register("bigfoot", ()->
            EntityType.Builder.of(BigfootEntity::new, EntityClassification.CREATURE)
                    .sized(1.0f,3.3f)
                    .build(new ResourceLocation(DoYouBelieve.MOD_ID, "bigfoot").toString()));
    public static final RegistryObject<EntityType<JerseyDevilEntity>> JERSEY_DEVIL = ENTITY_TYPES.register("jersey_devil", ()->
            EntityType.Builder.of(JerseyDevilEntity::new, EntityClassification.MONSTER)
                    .sized(1.0f,3.3f)
                    .build(new ResourceLocation(DoYouBelieve.MOD_ID, "jersey_devil").toString()));
    public static final RegistryObject<EntityType<FresnoEntity>> FRESNO = ENTITY_TYPES.register("fresno_nightcrawler", ()->
            EntityType.Builder.of(FresnoEntity::new, EntityClassification.MONSTER)
                    .sized(1.0f,1.5f)
                    .build(new ResourceLocation(DoYouBelieve.MOD_ID, "fresno_nightcrawler").toString()));
    public static final RegistryObject<EntityType<MothmanEntity>> MOTHMAN = ENTITY_TYPES.register("mothman", ()->
            EntityType.Builder.of(MothmanEntity::new, EntityClassification.MONSTER)
                    .sized(1.2f,2.5f)
                    .build(new ResourceLocation(DoYouBelieve.MOD_ID, "mothman").toString()));


}
