package com.littleezra.ethereal.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

public class PlayerFairyAggressor {
    private boolean hurtOak;
    private boolean hurtFairy;

    public boolean getHurtOak(){
        return hurtOak;
    }
    public boolean getHurtFairy(){
        return hurtFairy;
    }

    public void setHurtOak(boolean hurtOak) {
        this.hurtOak = hurtOak;
    }

    public void setHurtFairy(boolean hurtFairy) {
        this.hurtFairy = hurtFairy;
    }

    public void copyFrom(PlayerFairyAggressor source){
        this.hurtOak = source.hurtOak;
        this.hurtFairy = source.hurtFairy;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putBoolean("hurtOak", hurtOak);
        nbt.putBoolean("hurtFairy", hurtFairy);
    }

    public void loadNBTData(CompoundTag nbt){
        hurtOak = nbt.getBoolean("hurtOak");
        hurtFairy = nbt.getBoolean("hurtFairy");
    }
}
