package com.mysticalmod.capabilities.item;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityItemLvl {

    public static Capability<ItemLvl> ITEM_LVL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

}