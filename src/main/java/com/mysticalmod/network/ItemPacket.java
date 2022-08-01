package com.mysticalmod.network;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import com.mysticalmod.capabilities.item.CapabilityItemLvl;
import com.mysticalmod.capabilities.item.ItemLvl;

import com.mysticalmod.client.ItemStackSyncManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemPacket {
	
	private final Component hoverName;
    private final int itemLvl;
    private final int itemRarity;
	
	public ItemPacket(ItemLvl lvl, Component hoverName) {
		this.hoverName = hoverName;
        this.itemLvl = lvl.getItemLvl();
        this.itemRarity = lvl.getItemRarity();
	}
	
	public ItemPacket(FriendlyByteBuf buf) {
		hoverName = buf.readComponent();
		itemLvl = buf.readInt();
		itemRarity = buf.readInt();
	}
	
	public void encode(FriendlyByteBuf buf){
		buf.writeComponent(hoverName);
        buf.writeInt(itemLvl);
        buf.writeInt(itemRarity);
    }
	
	public void handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			System.out.println("handle");
			ItemStackSyncManager.receive(this.itemLvl, this.itemRarity, this.hoverName);
		});
		ctx.get().setPacketHandled(true);
	}
}
