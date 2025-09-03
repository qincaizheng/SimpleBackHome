package com.qdd.sbh.cap;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public class SBHData implements INBTSerializable<CompoundTag> {
    private Vec3 back = Vec3.ZERO;
    private Vec3 home = Vec3.ZERO;

    public Vec3 getback() {
        return this.back;
    }

    public void setback(Vec3 back) {
        this.back = back;
    }

    public Vec3 getHome() {
        return this.home;
    }

    public void setHome(Vec3 home) {
        this.home = home;
    }


    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putIntArray("talent", readvec3( back));
        tag.putIntArray("back", readvec3( home));
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        back = writevec3(tag.getIntArray("back"));
        home = writevec3(tag.getIntArray("home"));
    }

    public int[] readvec3(Vec3 back){
        return new int[]{(int)back.x, (int)back.y, (int)back.z};
    }
    public Vec3 writevec3(int[] back){
        return new Vec3(back[0], back[1], back[2]);
    }
}
