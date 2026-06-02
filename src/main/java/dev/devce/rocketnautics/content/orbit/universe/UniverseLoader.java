package dev.devce.rocketnautics.content.orbit.universe;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import dev.devce.rocketnautics.RocketNautics;
import dev.devce.rocketnautics.content.orbit.universe.builder.PlanetDefinitionBuilder;
import dev.devce.rocketnautics.content.orbit.universe.builder.UniverseDefinitionBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectRBTreeSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

// ImageIO for loading image overrides into palettes
public final class UniverseLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();
    public static final UniverseLoader INSTANCE = new UniverseLoader();

    public static final String DIRECTORY = "universe_planets";
    public static final ResourceLocation ID = RocketNautics.path(DIRECTORY);

    private static final Lazy<UniverseDefinition> FALLBACK = Lazy.of(() -> StandardUniverseProvider.createSolarSystem().build());
    private @Nullable UniverseDefinition built;

    private UniverseLoader() {
        super(GSON, DIRECTORY);
    }

    public @NotNull UniverseDefinition getLoaded() {
        if (built == null) {
            return FALLBACK.get();
        }
        return built;
    }

    @Override
    protected void apply(@NonNull Map<ResourceLocation, JsonElement> p_10793_, @NonNull ResourceManager p_10794_, @NonNull ProfilerFiller p_10795_) {
        // stage one: gather files
        Map<String, ObjectRBTreeSet<PlanetDefinitionBuilder>> buildersByName = new Object2ObjectOpenHashMap<>();
        for (var entry : p_10793_.entrySet()) {
            try {
                DataResult<PlanetDefinitionBuilder> result = PlanetDefinitionBuilder.CODEC.parse(JsonOps.INSTANCE, entry.getValue());

                if (result.isError()) {
                    RocketNautics.LOGGER.error(String.valueOf(result.error().orElse(null)));
                }

                PlanetDefinitionBuilder read = result.getOrThrow();
                var builders = buildersByName.computeIfAbsent(read.name, k -> new ObjectRBTreeSet<>(Comparator.comparingInt(b -> b.priority)));
                PlanetDefinitionBuilder samePriority = builders.get(read);
                if (samePriority == null) {
                    builders.add(read);
                } else {
                    builders.remove(samePriority);
                    builders.add(samePriority.subsume(read));
                }
            } catch (Exception e) {
                RocketNautics.LOGGER.error("Error while loading planet data [{}]: {}", entry.getKey(), e);
            }
        }
        // stage two: collapse builders and build dependency tree
        Map<String, PlanetDefinitionBuilder> collapsed = new Object2ObjectOpenHashMap<>();
        Map<String, Set<String>> dependents = new Object2ObjectOpenHashMap<>();
        Map<String, Set<String>> unsatisfiedDependencies = new Object2ObjectOpenHashMap<>();
        for (var value : buildersByName.values()) {
            if (value.isEmpty()) continue;
            PlanetDefinitionBuilder canonical = null;
            for (PlanetDefinitionBuilder builder : value) {
                if (canonical == null) {
                    canonical = builder;
                }
                canonical = builder.subsume(canonical);
            }
            collapsed.put(canonical.name, canonical);
            unsatisfiedDependencies.put(canonical.name, new ObjectOpenHashSet<>(canonical.dependencies));
            for (String dep : canonical.dependencies) {
                dependents.computeIfAbsent(dep, k -> new ObjectOpenHashSet<>()).add(canonical.name);
            }
        }
        // stage three: process in order of dependencies
        UniverseDefinitionBuilder building = new UniverseDefinitionBuilder();
        Deque<String> ready = new ArrayDeque<>();
        Set<String> knownUnloadable = new ObjectOpenHashSet<>();
        ready.addFirst("root");
        while (!ready.isEmpty()) {
            String constructing = ready.pop();
            PlanetDefinitionBuilder planet = collapsed.get(constructing);
            boolean fail = false;
            if (planet != null) {
                try {
                    building.cubePlanet(planet);
                } catch (IllegalStateException e) {
                    RocketNautics.LOGGER.error("Failed to load planet [{}] due to error {}", constructing, e);
                    fail = true;
                }
            }
            if (dependents.containsKey(constructing)) {
                for (String dependent : dependents.get(constructing)) {
                    if (fail) {
                        knownUnloadable.add(dependent);
                        RocketNautics.LOGGER.error("Planet [{}] cannot load as consequence of earlier error", dependent);
                    } else {
                        Set<String> deps = unsatisfiedDependencies.get(dependent);
                        deps.remove(constructing);
                        if (deps.isEmpty()) {
                            unsatisfiedDependencies.remove(dependent);
                            ready.addLast(dependent);
                        }
                    }
                }
            }
        }
        built = building.build();
        // stage four: report circular dependency
        for (var leftover : unsatisfiedDependencies.entrySet()) {
            if (knownUnloadable.contains(leftover.getKey())) continue;
            RocketNautics.LOGGER.error("Failed to load planet [{}] likely due to circular dependency; remaining dependencies are [{}]", leftover.getKey(), leftover.getValue());
        }
    }
}
