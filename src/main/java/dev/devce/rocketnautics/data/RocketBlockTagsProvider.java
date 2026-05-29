package dev.devce.rocketnautics.data;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import dev.devce.rocketnautics.RocketNautics;
import dev.devce.rocketnautics.registry.RocketTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RocketBlockTagsProvider {

    protected static void addTags(RegistrateTagsProvider.IntrinsicImpl<Block> prov) {
        prov.addTag(RocketTags.BlockTags.RILLE_CARVABLE.tag).add(Blocks.BASALT);
        prov.addTag(RocketTags.BlockTags.CRATER_CARVABLE.tag).add(Blocks.BASALT);
    }
}
