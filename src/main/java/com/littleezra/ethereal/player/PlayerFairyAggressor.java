package com.littleezra.ethereal.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

public class PlayerFairyAggressor {
    private int hurtOak;
    private int hurtFairy;

    public int getHurtOak(){
        return hurtOak;
    }
    public int getHurtFairy(){
        return hurtFairy;
    }

    public void setHurtOak(int hurtOak) {
        this.hurtOak = hurtOak;
    }

    public void setHurtFairy(int hurtFairy) {
        this.hurtFairy = hurtFairy;
    }

    public void addHurtOak(int value){
        hurtOak += value;
    }
    public void addHurtFairy(int value){
        hurtFairy += value;
    }
    public void subHurtOak(int value){
        hurtOak -= value;
    }
    public void subHurtFairy(int value){
        hurtFairy -= value;
    }

    public void copyFrom(PlayerFairyAggressor source){
        this.hurtOak = source.hurtOak;
        this.hurtFairy = source.hurtFairy;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("hurtOak", hurtOak);
        nbt.putInt("hurtFairy", hurtFairy);
    }

    public void loadNBTData(CompoundTag nbt){
        hurtOak = nbt.getInt("hurtOak");
        hurtFairy = nbt.getInt("hurtFairy");
    }
}
