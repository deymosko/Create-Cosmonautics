package dev.devce.rocketnautics.content.world;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class CraterWorldCarver extends WorldCarver<CraterCarverConfiguration> {

    private final Cache<IntIntPair, Integer> cachedYLevels = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(Duration.ofSeconds(10))
            .build();

    public CraterWorldCarver(Codec<CraterCarverConfiguration> p_159366_) {
        super(p_159366_);
    }

    @Override
    public boolean carve(CarvingContext context, CraterCarverConfiguration config, ChunkAccess chunkAccess, Function<BlockPos, Holder<Biome>> biome, RandomSource randomSource, Aquifer aquifer, ChunkPos chunkPos, CarvingMask carvingMask) {
        int posX = chunkPos.getBlockX(randomSource.nextInt(16));
        int posZ = chunkPos.getBlockZ(randomSource.nextInt(16));
        ChunkGenerator generator = ((IHasChunkGenerator) context).rocketnautics$getGenerator();
        int y;
        try {
            y = cachedYLevels.get(IntIntPair.of(posX, posZ), () -> generator.getFirstOccupiedHeight(posX, posZ, Heightmap.Types.WORLD_SURFACE_WG, chunkAccess.getHeightAccessorForGeneration(), context.randomState()));
        } catch (ExecutionException e) {
            return false;
        }
        float radius = config.radius.sample(randomSource);
        float horizontal = config.horizontalRadiusFactor.sample(randomSource);
        float height = config.heightOffsetFactor.sample(randomSource);
        this.carveEllipsoid(
                context,
                config,
                chunkAccess,
                biome,
                aquifer,
                posX,
                y + radius * height,
                posZ,
                radius * horizontal,
                radius,
                carvingMask,
                (p_159082_, xOffset, yOffset, zOffset, absoluteY) -> xOffset * xOffset + yOffset * yOffset + zOffset * zOffset >= 1
        );
        return true;
    }

    @Override
    public boolean isStartChunk(CraterCarverConfiguration config, RandomSource randomSource) {
        return randomSource.nextFloat() < config.probability;
    }
}
