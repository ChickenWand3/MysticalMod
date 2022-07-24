package com.mysticalmod.capabilities.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mysticalmod.network.ClientboundPlayerSkillsUpdateMessage;
import com.mysticalmod.network.SimpleNetworkHandler;

public class PlayerSkillsProvider implements ICapabilitySerializable<CompoundTag> {
    private final DefaultPlayerSkills skills = new DefaultPlayerSkills();
    private final LazyOptional<PlayerSkills> playerSkillsOptional = LazyOptional.of(() -> skills);

    public void invalidate() {
        playerSkillsOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return playerSkillsOptional.cast();
    }
    

    @Override
    public CompoundTag serializeNBT() {
        if (CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY == null) {
            return new CompoundTag();
        } else {
            CompoundTag compoundNBT = new CompoundTag();
            compoundNBT.putInt("combatLvl", skills.combatLvl);
            compoundNBT.putInt("combatXp", skills.combatXp);

            return compoundNBT;
        }
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY != null) {
            int combatLvl = nbt.getInt("combatLvl");
            int combatXp = nbt.getInt("combatXp");
            skills.combatLvl = (combatLvl);
            skills.combatXp = (combatXp);

        }
    }
    
    
    
    public static void levelClientUpdate(Player player) {
        if (!player.level.isClientSide()) {
        	System.out.println("Player levelClientUpdate");
            ServerPlayer serverPlayer = (ServerPlayer) player;
            player.getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).ifPresent(cap ->
                    SimpleNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> serverPlayer),
                            new ClientboundPlayerSkillsUpdateMessage(cap.getLvl(), cap.getXp())));
        }
    }

}