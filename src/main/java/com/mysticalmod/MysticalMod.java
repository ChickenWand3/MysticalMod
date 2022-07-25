package com.mysticalmod;

import com.mojang.logging.LogUtils;
import com.mysticalmod.capabilities.item.ItemLvlEventHandler;
import com.mysticalmod.capabilities.item.ItemLvlProvider;
import com.mysticalmod.capabilities.player.PlayerSkillsEventHandler;
import com.mysticalmod.network.SimpleNetworkHandler;
import com.mysticalmod.painting.ModPaintings;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

@Mod(MysticalMod.MODID)
public class MysticalMod
{
    public static final String MODID = "mysticalmod";
    
    private static final Logger LOGGER = LogUtils.getLogger();


    public MysticalMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        ModPaintings.register(modEventBus);

        
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    	//event.enqueueWork(SimpleNetworkHandler::init);
    	SimpleNetworkHandler.init();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
