package dev.devce.rocketnautics.registry;

import dev.devce.rocketnautics.RocketNautics;
import dev.devce.rocketnautics.content.world.ConfigurablyBiasedToBottomFloat;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.FloatProviderType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RocketFloatProviders {
    public static final DeferredRegister<FloatProviderType<?>> PROVIDERS = DeferredRegister.create(Registries.FLOAT_PROVIDER_TYPE, RocketNautics.MODID);

    public static final DeferredHolder<FloatProviderType<?>, FloatProviderType<ConfigurablyBiasedToBottomFloat>> VERY_BIASED_TO_BOTTOM = PROVIDERS.register("config_biased_to_bottom", () -> () -> ConfigurablyBiasedToBottomFloat.CODEC);

    public static void register(IEventBus eventBus) {
        PROVIDERS.register(eventBus);
    }
}
