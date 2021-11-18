package com.hugomage.doyoubelieve.registry;

import com.hugomage.doyoubelieve.DoYouBelieve;
import com.minecraftabnormals.abnormals_core.core.util.BiomeUtil;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class DYBBiomes {
/*    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DoYouBelieve.MOD_ID);

    public static final RegistryObject<Biome> BERMUDA_TRIANGLE = BIOMES.register("bermuda_triangle", () -> new JellyfishFields().getBiome());

    public static void init() {
        BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void registerBiomes() {
        registerBiome(BERMUDA_TRIANGLE.get(), BiomeManager.BiomeType.WARM, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.OVERWORLD);
    }

    public static void registerBiome(Biome biome, BiomeManager.BiomeType type, BiomeDictionary.Type... types) {
        BiomeDictionary.addTypes(RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DoYouBelieve.MOD_ID, "bermuda_triangle")), types);
        if (ModList.get().isLoaded("abnormals_core")) {
            RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DoYouBelieve.MOD_ID, "bermuda_triangle"));
            registerBiome(key, key, 15);
        }
        else {
            BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DoYouBelieve.MOD_ID, "bermuda_triangle")), 15));
        }
    }

    public static void registerBiome(RegistryKey<Biome> biome, @Nullable RegistryKey<Biome> deep, int weight) {
        BiomeUtil.addOceanBiome(BiomeUtil.OceanType.WARM, biome, deep, weight);
    }*/
}
