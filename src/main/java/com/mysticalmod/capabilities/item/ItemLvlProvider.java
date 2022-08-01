package com.mysticalmod.capabilities.item;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mysticalmod.network.ItemPacket;
import com.mysticalmod.network.PacketHandler;

public class ItemLvlProvider implements ICapabilitySerializable<CompoundTag> {
    private final DefaultItemLvl lvl = new DefaultItemLvl();
    private final LazyOptional<ItemLvl> itemLvlOptional = LazyOptional.of(() -> lvl);

    public void invalidate() {
    	itemLvlOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return itemLvlOptional.cast();
    }
    

    @Override
    public CompoundTag serializeNBT() {
        if (CapabilityItemLvl.ITEM_LVL_CAPABILITY == null) {
            return new CompoundTag();
        } else {
            CompoundTag compoundNBT = new CompoundTag();
            compoundNBT.putInt("itemLvl", lvl.getItemLvl());
            compoundNBT.putInt("itemRarity", lvl.getItemRarity());

            return compoundNBT;
        }
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (CapabilityItemLvl.ITEM_LVL_CAPABILITY != null) {
            int itemLvl = nbt.getInt("itemLvl");
            int itemRarity = nbt.getInt("itemRarity");
            lvl.setItemLvl(itemLvl);
            lvl.setItemRarity(itemRarity);
        }
    }
    
    

    public static void levelClientUpdate(Player player, ItemStack updateItem) {
        if (!player.level.isClientSide()) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            updateItem.getCapability(CapabilityItemLvl.ITEM_LVL_CAPABILITY).ifPresent(cap ->
            PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer),
                    new ItemPacket(cap, updateItem.getHoverName())));
		}
    }
}