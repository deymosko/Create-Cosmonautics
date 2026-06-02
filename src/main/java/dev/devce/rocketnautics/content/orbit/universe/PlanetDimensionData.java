package dev.devce.rocketnautics.content.orbit.universe;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public record PlanetDimensionData(@NotNull ResourceKey<Level> key, int transitionHeight, boolean renderUniverseInDimension,
                                  int dimensionDayTimeControllerID, boolean applyGravityCorrectionToEntities) {

    public boolean controlsDimensionDayTime() {
        return dimensionDayTimeControllerID >= 0;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeResourceKey(key);
        buf.writeVarInt(transitionHeight);
        buf.writeBoolean(renderUniverseInDimension);
        buf.writeVarInt(dimensionDayTimeControllerID);
        buf.writeBoolean(applyGravityCorrectionToEntities);
    }

    public static PlanetDimensionData read(FriendlyByteBuf buf) {
        ResourceKey<Level> key = buf.readResourceKey(Registries.DIMENSION);
        int transitionHeight = buf.readVarInt();
        boolean renderUniverseInDimension = buf.readBoolean();
        int controlDimensionDayTimeID = buf.readVarInt();
        boolean applyGravityCorrectionToEntities = buf.readBoolean();
        return new PlanetDimensionData(key, transitionHeight, renderUniverseInDimension, controlDimensionDayTimeID, applyGravityCorrectionToEntities);
    }
}
