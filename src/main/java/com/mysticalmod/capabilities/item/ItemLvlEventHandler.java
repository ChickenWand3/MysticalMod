package com.mysticalmod.capabilities.item;

import java.util.UUID;

import com.mysticalmod.MysticalMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MysticalMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemLvlEventHandler {
	
	protected static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
	
	@SubscribeEvent
	public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<ItemStack> event) {
		if (event.getObject().isEnchantable()) {
			ItemLvlProvider providerlvl = new ItemLvlProvider();
			event.addCapability(new ResourceLocation(MysticalMod.MODID, "mysticalmod"), providerlvl);
			event.addListener(providerlvl::invalidate);
		}
	}

	/*
	@SubscribeEvent
	public void registerCaps(RegisterCapabilitiesEvent event) {
		event.register(ItemLvl.class);
	}
	*/
	
	
	
	

	
	
	
	
	
}