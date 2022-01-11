package com.hugomage.doyoubelieve;

import com.hugomage.doyoubelieve.common.entities.BigfootEntity;
import com.hugomage.doyoubelieve.common.entities.FresnoEntity;
import com.hugomage.doyoubelieve.common.entities.JerseyDevilEntity;
import com.hugomage.doyoubelieve.common.entities.MothmanEntity;
import com.hugomage.doyoubelieve.registry.*;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(DoYouBelieve.MOD_ID)
public class DoYouBelieve {
    public static final String MOD_ID = "doyoubelieve";
    private static final Logger LOGGER = LogManager.getLogger();

    public DoYouBelieve() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        DYBItems.ITEMS.register(bus);
        DYBBlocks.BLOCKS.register(bus);
        DYBEntities.ENTITIES.register(bus);
        DYBSounds.SOUNDS.register(bus);
        DYBBiomes.BIOMES.register(bus);

        bus.addListener(this::createEntityAttributes);
        bus.addListener(this::registerCommon);

        GeckoLib.initialize();
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        DYBBiomes.registerBiomes();
    }

    private void createEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(DYBEntities.BIGFOOT.get(), BigfootEntity.setCustomAttributes().build());
        event.put(DYBEntities.JERSEY_DEVIL.get(), JerseyDevilEntity.setCustomAttributes().build());
        event.put(DYBEntities.FRESNO.get(), FresnoEntity.setCustomAttributes().build());
        event.put(DYBEntities.MOTHMAN.get(), MothmanEntity.setCustomAttributes().build());
        event.put(DYBEntities.MARTIAN.get(), MothmanEntity.setCustomAttributes().build());
    }

}
