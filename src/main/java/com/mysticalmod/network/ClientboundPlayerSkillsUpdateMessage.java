package com.mysticalmod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

import com.mysticalmod.capabilities.player.CapabilityPlayerSkills;

public class ClientboundPlayerSkillsUpdateMessage implements INormalMessage {
    int COMBAT_LVL;
    int COMBAT_XP;

    public ClientboundPlayerSkillsUpdateMessage(int combat_level, int combat_xp) {
        this.COMBAT_LVL = combat_level;
        this.COMBAT_XP = combat_xp;
    }

    public ClientboundPlayerSkillsUpdateMessage(FriendlyByteBuf buf) {
        COMBAT_LVL = buf.readInt();
        COMBAT_XP = buf.readInt();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(COMBAT_LVL);
        buf.writeInt(COMBAT_XP);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> context) {
        //This method is for when information is received by the intended end (i.e, client in this case)
        //We can ignore login to server/client for NetworkDirection, its used for internal forge stuff
        //Remember that client/server side rules apply here
        //Access client stuff only in client, otherwise you will crash MC
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.get().enqueueWork(() -> Minecraft.getInstance().player.getCapability(CapabilityPlayerSkills.PLAYER_STATS_CAPABILITY).
                    ifPresent(cap -> {
                        //do stuff with the info, such as mainly syncing info for the client-side gui
                    	System.out.println("Player Process");
                        cap.setLvl(COMBAT_LVL);
                        cap.setXp(COMBAT_XP);
                    }));
        }
    }
}
