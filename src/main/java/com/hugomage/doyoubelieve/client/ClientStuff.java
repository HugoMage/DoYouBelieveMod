package com.hugomage.doyoubelieve.client;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.client.renderer.*;
import com.hugomage.doyoubelieve.client.screens.BulletinBoardGui;
import com.hugomage.doyoubelieve.common.item.DYBSpawnEggItem;
import com.hugomage.doyoubelieve.registry.DYBBlocks;
import com.hugomage.doyoubelieve.registry.DYBContainers;
import com.hugomage.doyoubelieve.registry.DYBEntities;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DoYouBelieve.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientStuff {

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(DYBEntities.JERSEY_DEVIL.get(), JerseyDevilRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(DYBEntities.FRESNO.get(), FresnoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(DYBEntities.MOTHMAN.get(), MothmanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(DYBEntities.BIGFOOT.get(), BigfootRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(DYBEntities.MARTIAN.get(), MartianRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(DYBEntities.RAYGUNLASER.get(), RayGunLaserRenderer::new);

        RenderTypeLookup.setRenderLayer(DYBBlocks.BIGFOOT_TRACKS.get(), RenderType.translucent());

        ScreenManager.register(DYBContainers.BULLETIN_BOARD.get(), BulletinBoardGui::new);
    }


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void itemColors(ColorHandlerEvent.Item event) {
        ItemColors handler = event.getItemColors();
        IItemColor eggColor = (stack, tintIndex) -> ((DYBSpawnEggItem) stack.getItem()).getColor(tintIndex);
        for (DYBSpawnEggItem e : DYBSpawnEggItem.UNADDED_EGGS) handler.register(eggColor, e);
    }
}
