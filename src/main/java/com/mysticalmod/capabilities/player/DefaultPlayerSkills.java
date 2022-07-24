package com.mysticalmod.capabilities.player;

import net.minecraft.world.entity.player.Player;

public class DefaultPlayerSkills implements PlayerSkills {

    public int combatLvl;
    public int combatXp;


    public void awardCombatXp(Player player, int xp) {
            combatXp+=xp;
            while(combatXp>=50*combatLvl) {
            	combatXp-=(50*combatLvl);
            	combatLvl++;
            }
            PlayerSkillsProvider.levelClientUpdate(player);
    }


    public void copyForRespawn(DefaultPlayerSkills oldStore, Player player) {
    	this.combatLvl = oldStore.combatLvl;
    	this.combatXp = Math.round(oldStore.combatXp*2/3);
    	PlayerSkillsProvider.levelClientUpdate(player);
    }
    
    public void setXp(int xp) {
    	this.combatXp = xp;
    }
    
    public void setLvl(int lvl) {
    	this.combatLvl = lvl;
    }
    
    public int getLvl() {
    	return this.combatLvl;
    }
    
    public int getXp() {
    	return this.combatXp;
    }

    
}