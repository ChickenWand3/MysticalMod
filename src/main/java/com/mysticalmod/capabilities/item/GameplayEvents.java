package com.mysticalmod.capabilities.item;

import java.util.Collection;
import java.util.List;

import com.mysticalmod.MysticalMod;
import com.mysticalmod.capabilities.ColorConstants;
import com.mysticalmod.capabilities.player.CapabilityPlayerSkills;
import com.mysticalmod.capabilities.player.DefaultPlayerSkills;

import com.mysticalmod.client.ItemStackSyncManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
//import net.minecraft.world.entity.player.Player;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = MysticalMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameplayEvents {
	
	
	@SubscribeEvent
	//Only runs on the Client
	public static void displayAttributes(final ItemTooltipEvent event) {
		event.getItemStack().getCapability(CapabilityItemLvl.ITEM_LVL_CAPABILITY).ifPresent(lvl -> {
			ItemStackSyncManager.onHovered(event.getItemStack());
			event.getToolTip().add(Component.literal(ColorConstants.BOLD + "Level " + lvl.getItemLvl()));
			event.getToolTip().add(Component.literal(lvl.getRarityAndColor() + " " + Math.round((lvl.getRarityMultiplier()-1)*100) + "% Boost"));
		});
		
	}
	
	@SubscribeEvent
	public static void setLevel(final ItemCraftedEvent event) {
		if (event.getCrafting().isEnchantable() && !event.getEntity().level.isClientSide) {
			event.getCrafting().getCapability(CapabilityItemLvl.ITEM_LVL_CAPABILITY).ifPresent(lvl -> {
				event.getEntity().getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(skills -> {
					DefaultItemLvl lvl1 = (DefaultItemLvl) lvl;
					DefaultPlayerSkills actualSkills = (DefaultPlayerSkills) skills;
					lvl1.determineLvl(actualSkills.combatLvl, event.getEntity(), event.getCrafting());
					lvl1.generateRarity(event.getEntity(), event.getCrafting());
					
					//Display
					String oldName = event.getCrafting().getDisplayName().getString();
					event.getCrafting().setHoverName(Component.literal("Lvl " + lvl1.getItemLvl() + " " + lvl1.getRarityAndColor() + " " + oldName));
					System.out.println(lvl1.getRarity());
				});
			});
		}
	}
	
	
	//Runs on the client and the server
	@SubscribeEvent
	public static void updateItemAttributes(final ItemAttributeModifierEvent event) {
		event.getItemStack().getCapability(CapabilityItemLvl.ITEM_LVL_CAPABILITY).ifPresent(lvl -> {
			if (event.getSlotType() == EquipmentSlot.MAINHAND) {
				event.clearModifiers();
				double baseDamage = getAttribute(event.getItemStack(), event, Attributes.ATTACK_DAMAGE);
				double actualDamage = (baseDamage+(lvl.getItemLvl()*0.25))*lvl.getRarityMultiplier()-baseDamage;
				event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier("Weapon modifier", actualDamage, AttributeModifier.Operation.ADDITION));
			}
		});
	}
	
	
	private static double getAttribute(ItemStack stack, ItemAttributeModifierEvent event, Attribute attribute) {
		Collection<AttributeModifier> collection = event.getOriginalModifiers().get(attribute);
		if (!collection.isEmpty()) {
			AttributeModifier attributemodifier = collection.iterator().next();
			double damage = attributemodifier.getAmount();
			return damage;
		}
		return 0;
		}

	

} 
