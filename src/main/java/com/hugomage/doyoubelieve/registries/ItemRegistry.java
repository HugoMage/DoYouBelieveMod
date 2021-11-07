package com.hugomage.doyoubelieve.registries;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.block.BigFeetCarpet;
import com.hugomage.doyoubelieve.item.ModSpawnEggItem;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DoYouBelieve.MOD_ID);

    // Spawn Egg
    public static final RegistryObject<ModSpawnEggItem> BIGFOOT_SPAWN_EGG = ITEMS.register("bigfoot_spawn_egg",() -> new ModSpawnEggItem(EntityRegistry.BIGFOOT, 0x702817, 0x5E4944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<ModSpawnEggItem> JERSEY_DEVIL_SPAWN_EGG = ITEMS.register("jersey_devil_spawn_egg",() -> new ModSpawnEggItem(EntityRegistry.JERSEY_DEVIL, 0x702817, 0x5E4944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<ModSpawnEggItem> FRESNO_NIGHTCRAWLER_SPAWN_EGG = ITEMS.register("fresno_nightcrawler_spawn_egg",() -> new ModSpawnEggItem(EntityRegistry.FRESNO, 0x802817, 0x5E4944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<ModSpawnEggItem> MOTHMAN_SPAWN_EGG = ITEMS.register("mothman_spawn_egg",() -> new ModSpawnEggItem(EntityRegistry.MOTHMAN, 0x802817, 0x5E4944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    // Block Items
    public static final RegistryObject<Item> BIGFOOT_TRACKS_ITEM = ITEMS.register("bigfoot_tracks", () -> new BlockItem(BlockRegistry.BIGFOOT_TRACKS.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));
}