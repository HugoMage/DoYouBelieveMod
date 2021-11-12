package com.hugomage.doyoubelieve;

import com.hugomage.doyoubelieve.client.ClientStuff;
import com.hugomage.doyoubelieve.entities.BigfootEntity;
import com.hugomage.doyoubelieve.entities.FresnoEntity;
import com.hugomage.doyoubelieve.entities.JerseyDevilEntity;
import com.hugomage.doyoubelieve.entities.MothmanEntity;
import com.hugomage.doyoubelieve.registry.DYBBlocks;
import com.hugomage.doyoubelieve.registry.DYBItems;
import com.hugomage.doyoubelieve.registry.DYBEntities;
import com.hugomage.doyoubelieve.registry.DYBSounds;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        bus.addListener(this::createEntityAttributes);
        bus.addListener(this::registerClient);
    }

    private void createEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(DYBEntities.BIGFOOT.get(), BigfootEntity.setCustomAttributes().build());
        event.put(DYBEntities.JERSEY_DEVIL.get(), JerseyDevilEntity.setCustomAttributes().build());
        event.put(DYBEntities.FRESNO.get(), FresnoEntity.setCustomAttributes().build());
        event.put(DYBEntities.MOTHMAN.get(), MothmanEntity.setCustomAttributes().build());
    }

    private void registerClient(FMLClientSetupEvent event) {
        ClientStuff.init();
    }
}
