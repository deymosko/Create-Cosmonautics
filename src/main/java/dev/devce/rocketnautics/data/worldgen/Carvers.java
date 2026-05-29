package dev.devce.rocketnautics.data.worldgen;

import dev.devce.rocketnautics.RocketNautics;
import dev.devce.rocketnautics.content.world.ConfigurablyBiasedToBottomFloat;
import dev.devce.rocketnautics.content.world.CraterCarverConfiguration;
import dev.devce.rocketnautics.content.world.RilleCarverConfiguration;
import dev.devce.rocketnautics.registry.RocketCarvers;
import dev.devce.rocketnautics.registry.RocketTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;

public class Carvers {
    public static final ResourceKey<ConfiguredWorldCarver<?>> SINUOUS_RILLE = register("sinuous_rille");
    public static final ResourceKey<ConfiguredWorldCarver<?>> STRAIGHT_RILLE = register("straight_rille");
    public static final ResourceKey<ConfiguredWorldCarver<?>> STANDARD_CRATER = register("standard_crater");

    public static void bootstrap(BootstrapContext<ConfiguredWorldCarver<?>> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        context.register(STRAIGHT_RILLE, RocketCarvers.RILLE_CARVER.get().configured(
                new RilleCarverConfiguration(
                        0.025f,
                        ConstantHeight.of(VerticalAnchor.top()),
                        ConstantFloat.of(1),
                        VerticalAnchor.bottom(),
                        CarverDebugSettings.DEFAULT,
                        blocks.getOrThrow(RocketTags.BlockTags.RILLE_CARVABLE.tag),
                        12,
                        9,
                        5,
                        10f,
                        0f,
                        3948672
                )
        ));
        context.register(SINUOUS_RILLE, RocketCarvers.RILLE_CARVER.get().configured(
                new RilleCarverConfiguration(
                        0.03f,
                        ConstantHeight.of(VerticalAnchor.top()),
                        ConstantFloat.of(1),
                        VerticalAnchor.bottom(),
                        CarverDebugSettings.DEFAULT,
                        blocks.getOrThrow(RocketTags.BlockTags.RILLE_CARVABLE.tag),
                        12,
                        9,
                        5,
                        60f,
                        50f,
                        459837463
                )
        ));
        context.register(STANDARD_CRATER, RocketCarvers.CRATER_CARVER.get().configured(
                new CraterCarverConfiguration(
                        0.05f,
                        ConstantHeight.of(VerticalAnchor.top()),
                        ConstantFloat.of(1),
                        VerticalAnchor.bottom(),
                        CarverDebugSettings.DEFAULT,
                        blocks.getOrThrow(RocketTags.BlockTags.CRATER_CARVABLE.tag),
                        ConfigurablyBiasedToBottomFloat.of(3, 16 * 6, 5, 3),
                        UniformFloat.of(0.4f, 0.6f),
                        UniformFloat.of(1f, 1.3f)
                )
        ));
    }

    private static ResourceKey<ConfiguredWorldCarver<?>> register(String name) {
        return ResourceKey.create(Registries.CONFIGURED_CARVER, RocketNautics.path(name));
    }
}
