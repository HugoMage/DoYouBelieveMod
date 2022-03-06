package com.hugomage.doyoubelieve.registry;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.client.renderer.RayGunRenderer;
import com.hugomage.doyoubelieve.common.item.DYBClueItem;
import com.hugomage.doyoubelieve.common.item.DYBClues;
import com.hugomage.doyoubelieve.common.item.DYBSpawnEggItem;

import com.hugomage.doyoubelieve.common.item.RayGunItem;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class DYBItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DoYouBelieve.MOD_ID);

    // Spawn Egg
    public static final RegistryObject<DYBSpawnEggItem> BIGFOOT_SPAWN_EGG = ITEMS.register("bigfoot_spawn_egg",() -> new DYBSpawnEggItem(DYBEntities.BIGFOOT, 0x702817, 0x5E4944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<DYBSpawnEggItem> JERSEY_DEVIL_SPAWN_EGG = ITEMS.register("jersey_devil_spawn_egg",() -> new DYBSpawnEggItem(DYBEntities.JERSEY_DEVIL, 0x702817, 0x5E4944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<DYBSpawnEggItem> FRESNO_NIGHTCRAWLER_SPAWN_EGG = ITEMS.register("fresno_nightcrawler_spawn_egg",() -> new DYBSpawnEggItem(DYBEntities.FRESNO, 0x802817, 0x5E4944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<DYBSpawnEggItem> MOTHMAN_SPAWN_EGG = ITEMS.register("mothman_spawn_egg",() -> new DYBSpawnEggItem(DYBEntities.MOTHMAN, 0x802817, 0x5E4944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<DYBSpawnEggItem> MARTIAN_SPAWN_EGG = ITEMS.register("martian_spawn_egg",() -> new DYBSpawnEggItem(DYBEntities.MARTIAN, 0x802817, 0x5E2944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    // Items
    public static final RegistryObject<RayGunItem> RAYGUN = ITEMS.register("raygun", () -> new RayGunItem(new Item.Properties().setISTER(() -> RayGunRenderer::new).setNoRepair()));
    public static final RegistryObject<Item> CLUE_STRING = ITEMS.register("clue_string", () -> new Item(new Item.Properties()));

    // Block Items
    public static final RegistryObject<Item> BIGFOOT_TRACKS_ITEM = ITEMS.register("bigfoot_tracks", () -> new BlockItem(DYBBlocks.BIGFOOT_TRACKS.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
    public static final RegistryObject<Item> BULLETIN_BOARD_ITEM = ITEMS.register("bulletin_board", () -> new BlockItem(DYBBlocks.BULLETIN_BOARD.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));

    // Clues
    static {
        for (DYBClues clue : DYBClues.values()) {
            ITEMS.register(clue.getId() + "_clue", () -> new DYBClueItem(clue));
        }
    }
}