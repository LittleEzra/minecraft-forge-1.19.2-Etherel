package com.littleezra.ethereal.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ExampleC2SPacket {
    public ExampleC2SPacket(){

    }

    public ExampleC2SPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
           // Server-side
            ServerPlayer player = context.getSender();

            player.sendSystemMessage(Component.literal("It actually works!"));
        });
        return true;
    }
}
