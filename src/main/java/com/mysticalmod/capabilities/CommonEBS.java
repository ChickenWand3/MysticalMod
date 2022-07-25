package com.mysticalmod.capabilities;

import com.mysticalmod.MysticalMod;
import com.mysticalmod.capabilities.item.ItemLvl;
import com.mysticalmod.capabilities.item.ItemLvlEventHandler;
import com.mysticalmod.capabilities.player.PlayerSkills;
import com.mysticalmod.capabilities.player.PlayerSkillsEventHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = MysticalMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEBS {
	
	@SubscribeEvent
	public static void onStaticCommonSetup(FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new ItemLvlEventHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerSkillsEventHandler());
	}
	
	@SubscribeEvent
	public void registerCaps(RegisterCapabilitiesEvent event) {
		event.register(ItemLvl.class);
		event.register(PlayerSkills.class);
	}
}
