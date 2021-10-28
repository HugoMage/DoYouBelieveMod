package com.hugomage.doyoubelieve.client;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.client.renderer.BigfootRenderer;
import com.hugomage.doyoubelieve.client.renderer.FresnoRenderer;
import com.hugomage.doyoubelieve.client.renderer.JerseyDevilRenderer;
import com.hugomage.doyoubelieve.client.renderer.MothmanRenderer;
import com.hugomage.doyoubelieve.entities.BigfootEntity;
import com.hugomage.doyoubelieve.item.ModSpawnEggItem;
import com.hugomage.doyoubelieve.registerys.ModEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DoYouBelieve.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientStuff {
    public static void init() {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.BIGFOOT.get(), BigfootRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.JERSEY_DEVIL.get(), JerseyDevilRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.FRESNO.get(), FresnoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.MOTHMAN.get(), MothmanRenderer::new);
    }


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void itemColors(ColorHandlerEvent.Item event) {
        ItemColors handler = event.getItemColors();
        IItemColor eggColor = (stack, tintIndex) -> ((ModSpawnEggItem) stack.getItem()).getColor(tintIndex);
        for (ModSpawnEggItem e : ModSpawnEggItem.UNADDED_EGGS) handler.register(eggColor, e);
    }
}
