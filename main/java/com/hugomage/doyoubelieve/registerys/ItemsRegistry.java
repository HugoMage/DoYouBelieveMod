package com.hugomage.doyoubelieve.registerys;


import com.hugomage.doyoubelieve.DoYouBelieve;
import com.hugomage.doyoubelieve.block.BigFeetCarpet;
import com.hugomage.doyoubelieve.item.ModSpawnEggItem;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemsRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DoYouBelieve.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DoYouBelieve.MOD_ID);


    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

    }



    // Items

    // SpawnEgg
    public static final RegistryObject<ModSpawnEggItem> BIGFOOT_SPAWN_EGG = ITEMS.register("bigfoot_spawn_egg",() -> new ModSpawnEggItem(ModEntityTypes.BIGFOOT, 0x702817, 0x5E4944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<ModSpawnEggItem> JERSEY_DEVIL_SPAWN_EGG = ITEMS.register("jersey_devil_spawn_egg",() -> new ModSpawnEggItem(ModEntityTypes.JERSEY_DEVIL, 0x702817, 0x5E4944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<ModSpawnEggItem> FRESNO_NIGHTCRAWLER_SPAWN_EGG = ITEMS.register("fresno_nightcrawler_spawn_egg",() -> new ModSpawnEggItem(ModEntityTypes.FRESNO, 0x802817, 0x5E4944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));
    public static final RegistryObject<ModSpawnEggItem> MOTHMAN_SPAWN_EGG = ITEMS.register("mothman_spawn_egg",() -> new ModSpawnEggItem(ModEntityTypes.MOTHMAN, 0x802817, 0x5E4944 , new Item.Properties().tab(ItemGroup.TAB_MATERIALS)));

    //Skulls


    // Music Disc

    // Armor
    //tools

    // Blocks
    public static final RegistryObject<BigFeetCarpet> BIGFOOT_TRACKS = BLOCKS.register("bigfoot_tracks", BigFeetCarpet::new);



    // Block Items
    public static final RegistryObject<Item> BIGFOOT_TRACKS_ITEM = ITEMS.register("bigfoot_tracks", () -> new BlockItem(BIGFOOT_TRACKS.get(), new Item.Properties().tab(ItemGroup.TAB_DECORATIONS)));

    }