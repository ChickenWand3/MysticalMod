package com.mysticalmod.capabilities.player;

import com.mysticalmod.MysticalMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MysticalMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerSkillsEventHandler {
	
	@SubscribeEvent
	public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof Player) {
			PlayerSkillsProvider providerskills = new PlayerSkillsProvider();
			event.addCapability(new ResourceLocation(MysticalMod.MODID, "mysticalmod"), providerskills);
		}
	}

	@SubscribeEvent
	public void registerCaps(RegisterCapabilitiesEvent event) {
		event.register(PlayerSkills.class);
	}
	
	
	
	
	
	
	
}