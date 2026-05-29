package dev.devce.rocketnautics.mixin;

import dev.devce.rocketnautics.content.world.IHasChunkGenerator;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CarvingContext.class)
public abstract class CarvingContextMixin implements IHasChunkGenerator {
    @Unique
    private ChunkGenerator rocketnautics$generator;

    protected CarvingContextMixin(ChunkGenerator rocketnautics$generator) {
        this.rocketnautics$generator = rocketnautics$generator;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void saveGenerator(NoiseBasedChunkGenerator p_224845_, RegistryAccess p_224846_, LevelHeightAccessor p_224847_, NoiseChunk p_224848_, RandomState p_224849_, SurfaceRules.RuleSource p_224850_, CallbackInfo ci) {
        this.rocketnautics$generator = p_224845_;
    }

    @Override
    public ChunkGenerator rocketnautics$getGenerator() {
        return rocketnautics$generator;
    }
}
