package com.mysticalmod.capabilities.player;

import net.minecraft.world.entity.player.Player;

public interface PlayerSkills {
    void awardCombatXp(Player player, int xp);

	void copyForRespawn(DefaultPlayerSkills oldStore, Player player);
	
	void setXp(int xp);
	void setLvl(int lvl);
	
	int getLvl();
	int getXp();
}