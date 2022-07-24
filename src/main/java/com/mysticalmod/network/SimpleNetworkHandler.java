package com.mysticalmod.network;

import com.google.common.base.Function;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class SimpleNetworkHandler {
    private static final String PROTOCOL_VERSION = "1.0.2";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("mysticalmod", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    //An init where we register our packets
    public static void init() {
        int index = 0;
        //This type of packet is server to client
        registerMessage(index++, ClientboundPlayerSkillsUpdateMessage.class, ClientboundPlayerSkillsUpdateMessage::new);
        registerMessage(index++, ClientboundItemLvlUpdateMessage.class, ClientboundItemLvlUpdateMessage::new);
    }

    private static <T extends INormalMessage> void registerMessage(int index, Class<T> messageType, Function<FriendlyByteBuf, T> decoder) {
        //Encoding is saving information to the byte buffer, decoding is the opposite of that (reading info)
        INSTANCE.registerMessage(index, messageType, INormalMessage::toBytes, decoder, (message, context) -> {
            message.process(context);
            context.get().setPacketHandled(true);
        });
    }
}
