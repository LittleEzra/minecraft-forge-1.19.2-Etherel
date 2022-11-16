package com.littleezra.ethereal.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerFairyAggressorProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerFairyAggressor> PLAYER_FAIRY_AGGRESSOR =
            CapabilityManager.get(new CapabilityToken<PlayerFairyAggressor>() {});

    private PlayerFairyAggressor aggressor = null;
    private final LazyOptional<PlayerFairyAggressor> optional = LazyOptional.of(this::createPlayerAggressor);

    private PlayerFairyAggressor createPlayerAggressor() {
        if (this.aggressor == null){
            this.aggressor = new PlayerFairyAggressor();
        }

        return this.aggressor;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_FAIRY_AGGRESSOR){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerAggressor().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerAggressor().loadNBTData(nbt);
    }
}
