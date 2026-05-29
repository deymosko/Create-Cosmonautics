package dev.devce.rocketnautics.data.worldgen.biome;

import com.google.common.collect.ImmutableMap;
import dev.devce.rocketnautics.data.worldgen.Carvers;
import dev.devce.rocketnautics.data.worldgen.PlacedFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeSpecialEffectsBuilder;

import java.util.List;

// TODO ambient sounds and such
public class OverworldMoonBiomes {
    public static Biome lunarMaria(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return new Biome.BiomeBuilder()
                .temperature(1)
                .downfall(0)
                .hasPrecipitation(false)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(0xdedede)
                        .waterColor(0xbacce0)
                        .waterFogColor(0xbacce0)
                        .skyColor(0xffffff)
                        .build())
                .generationSettings(new BiomeGenerationSettings(
                        ImmutableMap.of(
                                GenerationStep.Carving.AIR,
                                HolderSet.direct(carvers.getOrThrow(Carvers.STRAIGHT_RILLE), carvers.getOrThrow(Carvers.SINUOUS_RILLE), carvers.getOrThrow(Carvers.STANDARD_CRATER))
                        ),
                        List.of()
                ))
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .build();
    }

    public static Biome lunarHighlands(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return new Biome.BiomeBuilder()
                .temperature(1)
                .downfall(0)
                .hasPrecipitation(false)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(0xdedede)
                        .waterColor(0xbacce0)
                        .waterFogColor(0xbacce0)
                        .skyColor(0xffffff)
                        .build())
                .generationSettings(new BiomeGenerationSettings(
                        ImmutableMap.of(
                                GenerationStep.Carving.AIR, // extra starting crater carving step compared to the maria. This also allows for overlapping small craters.
                                HolderSet.direct(carvers.getOrThrow(Carvers.STANDARD_CRATER), carvers.getOrThrow(Carvers.STRAIGHT_RILLE), carvers.getOrThrow(Carvers.SINUOUS_RILLE), carvers.getOrThrow(Carvers.STANDARD_CRATER))
                        ),
                        List.of(
                                HolderSet.direct(features.getOrThrow(PlacedFeatures.LUNAR_ROCK), features.getOrThrow(PlacedFeatures.LUNAR_MOSS))
                        )
                ))
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .build();
    }

    public static Biome lunarBasaltChasm(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return new Biome.BiomeBuilder()
                .temperature(1)
                .downfall(0)
                .hasPrecipitation(false)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(0xdedede)
                        .waterColor(0xbacce0)
                        .waterFogColor(0xbacce0)
                        .skyColor(0xffffff)
                        .build())
                .generationSettings(BiomeGenerationSettings.EMPTY)
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .build();
    }

    public static Biome lunarAgedChasm(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return lunarBasaltChasm(features, carvers);
    }

    public static Biome lunarBasaltSpikes(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return new Biome.BiomeBuilder()
                .temperature(1)
                .downfall(0)
                .hasPrecipitation(false)
                .specialEffects(new BiomeSpecialEffects.Builder()
                        .fogColor(0xdedede)
                        .waterColor(0xbacce0)
                        .waterFogColor(0xbacce0)
                        .skyColor(0xffffff)
                        .build())
                .generationSettings(BiomeGenerationSettings.EMPTY)
                .mobSpawnSettings(MobSpawnSettings.EMPTY)
                .build();
    }

    public static Biome lunarAgedSpikes(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        return lunarBasaltSpikes(features, carvers);
    }

}
