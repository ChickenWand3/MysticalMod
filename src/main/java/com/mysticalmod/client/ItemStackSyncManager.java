package com.mysticalmod.client;

import com.mysticalmod.capabilities.item.CapabilityItemLvl;
import com.mysticalmod.network.PacketHandler;
import com.mysticalmod.network.RequestItemPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.mutable.MutableObject;

public class ItemStackSyncManager {

    public static void onHovered(ItemStack stack) {
        System.out.println("OnHovered");
        if (!stack.is(Items.AIR)) {
            System.out.println("Not Air");
            PacketHandler.INSTANCE.sendToServer(new RequestItemPacket(stack.getHoverName()));
        }
    }

    public static void receive(int itemLvl, int itemRarity, Component hoverName) {
        NonNullList<ItemStack> better = Minecraft.getInstance().player.containerMenu.getItems();
        for (ItemStack carried : better) {
            if (carried.getHoverName().equals(hoverName)) {
                carried.getCapability(CapabilityItemLvl.ITEM_LVL_CAPABILITY).ifPresent(cap -> {
                    System.out.println("Fully received!");
                    cap.setItemLvl(itemLvl);
                    cap.setItemRarity(itemRarity);
                });
            }
        }
    }
}
