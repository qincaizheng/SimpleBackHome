package com.qdd.sbh.cap;

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

public class SBHCapProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<SBHData> PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>() {
    });

    private SBHData data = null;
    private final LazyOptional<SBHData> optional = LazyOptional.of(this::createPlayerCap);
    private SBHData createPlayerCap() {
        if(this.data == null) {
            this.data = new SBHData();
        }

        return this.data;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_DATA) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return createPlayerCap().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerCap().deserializeNBT(nbt);
    }
}
