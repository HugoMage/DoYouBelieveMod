package com.hugomage.doyoubelieve.common.world.biome;

import com.hugomage.doyoubelieve.DoYouBelieve;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BermudaTriangleBiome extends Biome.Builder {
    static final ConfiguredSurfaceBuilder<?> SURFACE_BUILDER = Registry.register(WorldGenRegistries.CONFIGURED_SURFACE_BUILDER, DoYouBelieve.MOD_ID + "bermuda_triangle", new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(Blocks.SAND.defaultBlockState(), Blocks.SAND.defaultBlockState(), Blocks.SAND.defaultBlockState())));
    static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder();
    static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = new BiomeGenerationSettings.Builder();

    public BermudaTriangleBiome() {
        this.specialEffects(new BiomeAmbience.Builder()
                .waterColor(4445678)
                .waterFogColor(270131)
                .fogColor(12638463)
                .skyColor(8103167)
                .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS)
                .build());
        this.downfall(0.4F);
        this.generationSettings(GENERATION_SETTINGS.surfaceBuilder(SURFACE_BUILDER).build());
        this.mobSpawnSettings(SPAWN_SETTINGS.build());
        this.precipitation(Biome.RainType.RAIN);
        this.temperature(0.8F);
        this.biomeCategory(Biome.Category.OCEAN);
        this.temperatureAdjustment(Biome.TemperatureModifier.NONE);
        this.depth(-1.25F);
        this.scale(0.15F);
        this.build();
    }

    static {
        GENERATION_SETTINGS.addStructureStart(StructureFeatures.RUINED_PORTAL_OCEAN);

        DefaultBiomeFeatures.addDefaultOres(GENERATION_SETTINGS);
    }
}