package dev.devce.rocketnautics.content.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class CraterCarverConfiguration extends CarverConfiguration {
    public static final Codec<CraterCarverConfiguration> CODEC = RecordCodecBuilder.create(p_158984_ -> p_158984_.group(
            CarverConfiguration.CODEC.forGetter(p_158990_ -> p_158990_),
            FloatProvider.CODEC.fieldOf("radius").forGetter(c -> c.radius),
            FloatProvider.CODEC.fieldOf("heightOffsetFactor").forGetter(c -> c.heightOffsetFactor),
            FloatProvider.CODEC.fieldOf("horizontalRadiusFactor").forGetter(c -> c.horizontalRadiusFactor)
            ).apply(p_158984_, CraterCarverConfiguration::new)
    );

    public final FloatProvider radius;
    public final FloatProvider heightOffsetFactor;
    public final FloatProvider horizontalRadiusFactor;

    public CraterCarverConfiguration(float p_224832_, HeightProvider p_224833_, FloatProvider p_224834_, VerticalAnchor p_224835_, CarverDebugSettings p_224836_, HolderSet<Block> p_224837_, FloatProvider radius, FloatProvider heightOffsetFactor, FloatProvider horizontalRadiusFactor) {
        super(p_224832_, p_224833_, p_224834_, p_224835_, p_224836_, p_224837_);
        this.radius = radius;
        this.heightOffsetFactor = heightOffsetFactor;
        this.horizontalRadiusFactor = horizontalRadiusFactor;
    }

    public CraterCarverConfiguration(CarverConfiguration configuration, FloatProvider radius, FloatProvider heightOffsetFactor, FloatProvider horizontalRadiusFactor) {
        this(configuration.probability, configuration.y, configuration.yScale, configuration.lavaLevel, configuration.debugSettings, configuration.replaceable, radius, heightOffsetFactor, horizontalRadiusFactor);
    }
}
