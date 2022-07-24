package com.mysticalmod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import com.mysticalmod.capabilities.item.CapabilityItemLvl;

public class ClientboundItemLvlUpdateMessage implements INormalMessage {
	ItemStack ITEM;
    int ITEM_LVL;
    int ITEM_RARITY;

    public ClientboundItemLvlUpdateMessage(ItemStack changeItem, int item_level, int item_rarity) {
    	this.ITEM = changeItem;
        this.ITEM_LVL = item_level;
        this.ITEM_RARITY = item_rarity;
    }

    public ClientboundItemLvlUpdateMessage(FriendlyByteBuf buf) {
    	ITEM = buf.readItem();
    	ITEM_LVL = buf.readInt();
    	ITEM_RARITY = buf.readInt();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
    	buf.writeItem(ITEM);
        buf.writeInt(ITEM_LVL);
        buf.writeInt(ITEM_RARITY);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.get().enqueueWork(() -> ITEM.getCapability(CapabilityItemLvl.ITEM_LVL_CAPABILITY).
                    ifPresent(cap -> {
                    	cap.setItemLvl(ITEM_LVL);
                    	cap.setItemRarity(ITEM_RARITY);
                        System.out.println("New Cap Level" + cap.getItemLvl());
                    }));
        }
    }

}
