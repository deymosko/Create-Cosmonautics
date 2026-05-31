package dev.devce.rocketnautics.content.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class RilleCarverConfiguration extends CarverConfiguration {
    public static final Codec<RilleCarverConfiguration> CODEC = RecordCodecBuilder.create(p_158984_ -> p_158984_.group(
            CarverConfiguration.CODEC.forGetter(p_158990_ -> p_158990_),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("maxConnectionDistance").forGetter(c -> c.maxConnectionDistance),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("minConnectionDistance").forGetter(c -> c.minConnectionDistance),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("searchLength").forGetter(c -> c.searchLength),
            Codec.FLOAT.fieldOf("maxAllowedAngle").forGetter(c -> c.maxAllowedAngle),
            Codec.FLOAT.fieldOf("minAllowedAngle").forGetter(c -> c.minAllowedAngle),
            Codec.INT.fieldOf("salt").forGetter(c -> c.salt)
            ).apply(p_158984_, RilleCarverConfiguration::new)
    );

    public final int maxConnectionDistance;
    public final int minConnectionDistance;
    public final int searchLength;
    public final float maxAllowedAngle;
    public final float minAllowedAngle;
    public final int salt;

    protected float minAllowedDot = -1;
    protected float maxAllowedDot = -1;

    public RilleCarverConfiguration(float p_224832_, HeightProvider p_224833_, FloatProvider p_224834_, VerticalAnchor p_224835_, CarverDebugSettings p_224836_, HolderSet<Block> p_224837_, int maxConnectionDistance, int minConnectionDistance, int searchLength, float maxAllowedAngle, float minAllowedAngle, int salt) {
        super(p_224832_, p_224833_, p_224834_, p_224835_, p_224836_, p_224837_);
        this.maxConnectionDistance = Math.clamp(maxConnectionDistance, 0, 17);
        this.minConnectionDistance = Math.clamp(minConnectionDistance, 0, maxConnectionDistance);
        this.searchLength = Math.max(0, searchLength);
        this.maxAllowedAngle = maxAllowedAngle;
        this.minAllowedAngle = minAllowedAngle;
        this.salt = salt;
    }

    public RilleCarverConfiguration(CarverConfiguration configuration, int maxConnectionDistance, int minConnectionDistance, int searchLength, float maxAllowedAngle, float minAllowedAngle, int salt) {
        this(configuration.probability, configuration.y, configuration.yScale, configuration.lavaLevel, configuration.debugSettings, configuration.replaceable, maxConnectionDistance, minConnectionDistance, searchLength, maxAllowedAngle, minAllowedAngle, salt);
    }

    public float getMinAllowedDot() {
        if (minAllowedDot == -1) {
            minAllowedDot = (float) Math.cos(Math.toRadians(maxAllowedAngle));
        }
        return minAllowedDot;
    }

    public float getMaxAllowedDot() {
        if (maxAllowedDot == -1) {
            maxAllowedDot = (float) Math.cos(Math.toRadians(minAllowedAngle));
        }
        return maxAllowedDot;
    }
}
