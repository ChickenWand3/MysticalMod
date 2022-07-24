package com.mysticalmod.capabilities.player;

import com.mysticalmod.MysticalMod;
import com.mysticalmod.capabilities.ColorConstants;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
//import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MysticalMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameplayEvents {

	
	@SubscribeEvent
	public static void showPlayer(PlayerTickEvent event) {
		event.player.getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(skills -> {
			if(!event.player.level.isClientSide) {
				String red;
				red = ColorConstants.RED;
				String yellow;
				yellow = ColorConstants.YELLOW;
				DefaultPlayerSkills actualSkills = (DefaultPlayerSkills) skills;
				event.player.displayClientMessage(Component
						.nullToEmpty(red + "Lvl " + actualSkills.combatLvl + yellow + "   Xp " + actualSkills.combatXp + "/" + (actualSkills.combatLvl*50)), true);
			}
		});
		
	}
	
	
	@SubscribeEvent
	public static void stickMagic(final PlayerInteractEvent.RightClickItem event) {
		if (event.getEntity().getMainHandItem().getItem() == Items.STICK) {
			
			Double rayLength = 100d;
			Vec3 playerRotation = event.getEntity().getViewVector(0);
			Vec3 rayPath = playerRotation.scale(rayLength);
			
			Vec3 from = event.getEntity().getEyePosition(0);
			Vec3 to = from.add(rayPath);
			
			ClipContext rayCtx = new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null);
			BlockHitResult rayHit = event.getLevel().clip(rayCtx);
			
			Vec3 hitLocation = rayHit.getLocation();
			
			event.getEntity().getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(skills -> {
				DefaultPlayerSkills actualSkills = (DefaultPlayerSkills) skills; 
			
				if(rayHit.getType() == HitResult.Type.MISS) {
					event.getEntity().level.playSound(null, event.getEntity(), SoundEvents.VILLAGER_HURT, SoundSource.PLAYERS, 1f, 1f);
				} 
				else if(rayHit.getType() == HitResult.Type.BLOCK){
					spawnTnt(event.getLevel(), event.getEntity(), hitLocation.x + 0.5D, hitLocation.y, hitLocation.z + 0.5D, actualSkills.combatLvl);
			}
			}); 

		}
	}
	
	@SubscribeEvent
	public static void awardCombatStats(final LivingDeathEvent event) {
		if (!event.getEntity().level.isClientSide && event.getSource().getEntity() instanceof Player && event.getEntity() instanceof Mob){
			Player player = (Player) event.getSource().getEntity();
			Mob target = (Mob) event.getEntity();
			player.getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(
					skills -> {
						DefaultPlayerSkills skills1 = (DefaultPlayerSkills) skills;
						System.out.println(target.getMaxHealth());
						skills1.awardCombatXp(player, Math.round(target.getMaxHealth()));
						player.level.playSound(null, player, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1f, 1f);
					});
		}
	}
	
	
	@SubscribeEvent
	public static void onDeath(final PlayerEvent.Clone event) {
	    if (!event.getEntity().level.isClientSide && event.isWasDeath()) {
	        event.getOriginal().reviveCaps();
	        
	        event.getOriginal().getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(oldStore -> {
	            event.getEntity().getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(newStore -> {
	            	DefaultPlayerSkills newskills1 = (DefaultPlayerSkills) newStore;
	            	DefaultPlayerSkills oldskills1 = (DefaultPlayerSkills) oldStore;
	            	newskills1.copyForRespawn(oldskills1, event.getEntity());
	            });
	        });
	        event.getOriginal().invalidateCaps();
	    }
	}
	
	
	public static void spawnTnt(Level world, Entity player, Double x, Double y, Double z, int lvl) {
		world.explode(player, null, null, x, y, z, lvl, true, Explosion.BlockInteraction.NONE);
	}
	
	
	/*
	@SubscribeEvent
	public static void testClientInfo(final PlayerTickEvent event) {
		if (event.player.level.isClientSide) {
			event.player.getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(skills -> {
				DefaultPlayerSkills actualSkills = (DefaultPlayerSkills) skills;
				System.out.println("Client says you are level " + actualSkills.getLvl());
			});
		}
	}
	*/
	




}
