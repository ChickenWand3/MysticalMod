package com.mysticalmod.capabilities.item;

import com.mysticalmod.capabilities.ColorConstants;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DefaultItemLvl implements ItemLvl {

    private int itemLvl;
    
    private int itemRarity;
    public String itemRarityName = "Bruh";
    private String[] names = {"Common", "Uncommon", "Rare", "Epic", "Legendary", "Mystical"};

    public void determineLvl(int xp, Player player, ItemStack item) {
    	itemLvl = xp;
    	ItemLvlProvider.levelClientUpdate(player, item);
    }
    
    public String getRarity() {
    	return itemRarityName;
    }
    
    public int getItemLvl() {
    	return itemLvl;
    }
    
    public void setItemLvl(int amount) {
    	this.itemLvl = amount;
    }
    
    public int getItemRarity() {
    	return itemRarity;
    }
    
    public void setItemRarity(int amount) {
    	this.itemRarity = amount;
    }
    
    public String getRarityAndColor() {
    	if (itemRarityName == null) {
    		setRarityName();
    	}
    	if (itemRarityName == "Mythical") {
    		return ColorConstants.RED + ColorConstants.BOLD + "Mythical";
    	} if (itemRarityName == "Legendary") {
    		return ColorConstants.GOLD + "Legendary";
    	} else if(itemRarityName == "Epic") {
    		return ColorConstants.DARK_PURPLE + "Epic";
    	} else if (itemRarityName == "Rare") {
    		return ColorConstants.BLUE + "Rare";
    	} else if (itemRarityName == "Uncommon") {
    		return ColorConstants.GREEN + "Uncommon";
    	} else {return ColorConstants.GRAY + "Common";}
    }
    
    
    public void generateRarity(Player player, ItemStack item) {
    	double i = Math.random();
    	if (i > .999d) {
    		itemRarity = 5;
    	} else if (i > .99d) {
    		itemRarity = 4;
    	} else if (i > .95d) {
    		itemRarity = 3;
    	} else if (i > .80d) {
    		itemRarity = 2;
    	} else if (i > .50d) {
    		itemRarity = 1;
    	} else {
    		itemRarity = 0;
    	}
    	setRarityName();
    	ItemLvlProvider.levelClientUpdate(player, item);
    }
    
    public float getRarityMultiplier() {
    	setRarityName();
    	if (itemRarity == 5) {
    		return 2.0f;
    	} else if (itemRarity == 4) {
    		return 1.5f;
    	} else if(itemRarity == 3) {
    		return 1.35f;
    	} else if (itemRarity == 2) {
    		return 1.25f;
    	} else if (itemRarity == 1) {
    		return 1.1f;
    	} else {return 1f;}
    }
    
    private void setRarityName() {
    	itemRarityName = names[itemRarity];
    }


    
}