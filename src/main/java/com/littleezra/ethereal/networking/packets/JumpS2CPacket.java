package com.littleezra.ethereal.networking.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class JumpS2CPacket {
    public JumpS2CPacket(){

    }

    public JumpS2CPacket(FriendlyByteBuf buf){

    }

    public void toBytes(FriendlyByteBuf buf){

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client-side
            ServerPlayer player = context.getSender();

            player.sendSystemMessage(Component.literal("Trying to Jump!"));
        });
        return true;
    }
}
