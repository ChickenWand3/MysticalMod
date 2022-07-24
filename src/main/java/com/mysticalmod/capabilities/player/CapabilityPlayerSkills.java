package com.mysticalmod.capabilities.player;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityPlayerSkills {

    public static Capability<PlayerSkills> PLAYER_STATS_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

}