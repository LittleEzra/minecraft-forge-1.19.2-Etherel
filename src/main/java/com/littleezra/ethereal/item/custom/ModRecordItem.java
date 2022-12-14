package com.littleezra.ethereal.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class ModRecordItem extends RecordItem {

    private String description;

    public ModRecordItem(int comparatorValue, Supplier<SoundEvent> soundSupplier, Properties builder, int lengthInTicks, String description) {
        super(comparatorValue, soundSupplier, builder, lengthInTicks);
        this.description = description;
    }

    @Override
    public MutableComponent getDisplayName() {
        return Component.literal(description);
    }
}
