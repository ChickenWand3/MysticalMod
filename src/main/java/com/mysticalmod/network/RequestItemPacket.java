package com.mysticalmod.network;

import java.util.function.Supplier;

import com.mysticalmod.capabilities.item.CapabilityItemLvl;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

public class RequestItemPacket {

	private final Component hoverName;
	
	public RequestItemPacket(Component name) {
		this.hoverName = name;
	}
	
	public RequestItemPacket(FriendlyByteBuf buf) {
		hoverName = buf.readComponent();
	}
	
	public void encode(FriendlyByteBuf buf) {
		buf.writeComponent(hoverName);
	}

	public void handle(Supplier<NetworkEvent.Context> context) {
		//This is a server side Method
		context.get().enqueueWork(() -> {
			System.out.println("Server handle");
			NonNullList<ItemStack> carried = context.get().getSender().containerMenu.getItems();
			for (ItemStack stack : carried) {
				System.out.println("stack " + stack.getHoverName());
				System.out.println("hover " + hoverName);
				if (stack.getHoverName().equals(hoverName)) {
					System.out.println("Hover Name matches");
					stack.getCapability(CapabilityItemLvl.ITEM_LVL_CAPABILITY).ifPresent(itemLvl -> PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(context.get()::getSender), new ItemPacket(itemLvl, hoverName)));
				}
			}
		});
	}
	
}
