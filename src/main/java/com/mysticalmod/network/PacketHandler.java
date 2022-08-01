package com.mysticalmod.network;

import com.mysticalmod.MysticalMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MysticalMod.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    private PacketHandler() {
    }

    public static void init() {
        int index = 0;
        INSTANCE.messageBuilder(ItemPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(ItemPacket::encode).decoder(ItemPacket::new)
                .consumerMainThread(ItemPacket::handle).add();
        
        
        INSTANCE.messageBuilder(PlayerPacket.class, index++, NetworkDirection.PLAY_TO_CLIENT)
        	.encoder(PlayerPacket::encode).decoder(PlayerPacket::new)
        	.consumerMainThread(PlayerPacket::handle).add();

        INSTANCE.messageBuilder(RequestItemPacket.class, index++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(RequestItemPacket::encode).decoder(RequestItemPacket::new)
                .consumerMainThread(RequestItemPacket::handle).add();
        
        MysticalMod.LOGGER.info("Registered {} packets for mod '{}'", index, MysticalMod.MODID);
    }

}
