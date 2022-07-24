package com.mysticalmod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

//A helper class to make packets easier
public interface INormalMessage {

    void toBytes(FriendlyByteBuf buf);

    void process(Supplier<NetworkEvent.Context> context);

}