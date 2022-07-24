package com.mysticalmod.painting;

import com.mysticalmod.MysticalMod;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPaintings {
	public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS =
			DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, MysticalMod.MODID);
	
	//Painting Registry
	public static final RegistryObject<PaintingVariant> PLANT = paintingMaker("plant", 16, 16);
	public static final RegistryObject<PaintingVariant> WANDERER = paintingMaker("wanderer", 16, 32);
	public static final RegistryObject<PaintingVariant> SUNSET = paintingMaker("sunset", 32, 16);
	public static final RegistryObject<PaintingVariant> CREEPERSUNSET = paintingMaker("creepersunset", 32, 32);
	public static final RegistryObject<PaintingVariant> BEACHGIRL = paintingMaker("beachgirl", 32, 32);
	public static final RegistryObject<PaintingVariant> JOSH = paintingMaker("josh", 64, 64);
	public static final RegistryObject<PaintingVariant> BATHROOM = paintingMaker("bathroom", 64, 64);
	public static final RegistryObject<PaintingVariant> TOILETWOMAN = paintingMaker("toiletwoman", 64, 64);
	public static final RegistryObject<PaintingVariant> CHAMBEROFREFLECTION = paintingMaker("chamberofreflection", 64, 64);
	public static final RegistryObject<PaintingVariant> TRUMPFADE = paintingMaker("trumpfade", 64, 64);
	public static final RegistryObject<PaintingVariant> BIDENSAD = paintingMaker("bidensad", 32, 32);
	
	public static void register(IEventBus bus) {
		PAINTING_VARIANTS.register(bus);
	}
	
	//Method to make paintings
	public static RegistryObject<PaintingVariant> paintingMaker(String name, int length, int width) {
		return PAINTING_VARIANTS.register(name, 
				() -> new PaintingVariant(length, width));
	}

}
