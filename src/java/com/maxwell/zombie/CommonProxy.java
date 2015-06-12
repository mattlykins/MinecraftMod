package com.maxwell.zombie;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.maxwell.zombie.blocks.ModBlocks;
import com.maxwell.zombie.entity.EntityMaxZombie;
import com.maxwell.zombie.items.ModItems;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e) {

		ModItems.createItems();
		ModBlocks.createBlocks();
		registerModEntityWithEgg(EntityMaxZombie.class, "maxzombie", 0x3F5505, 0x4E6414);

	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {

	}

	public void registerModEntityWithEgg(Class parEntityClass,String parEntityName, int parEggColor, int parEggSpotsColor) {
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerModEntity(parEntityClass, parEntityName,	entityID, Main.instance, 80, 3, false);
//		registerSpawnEgg(parEntityName, parEggColor, parEggSpotsColor);
	}
}
