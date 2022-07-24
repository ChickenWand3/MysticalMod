package com.mysticalmod.capabilities.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface ItemLvl {
    void determineLvl(int xp, Player player, ItemStack item);
    
    int getItemLvl();
    void setItemLvl(int amount);
    int getItemRarity();
    void setItemRarity(int amount);
    
    String getRarity();
    String getRarityAndColor();
    
    void generateRarity(Player player, ItemStack item);
    
    float getRarityMultiplier();

}