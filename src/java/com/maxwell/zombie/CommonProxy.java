package com.maxwell.zombie;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
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
		
		BiomeDictionary.registerAllBiomesAndGenerateEvents();

		BiomeGenBase[] allBiomes = Iterators.toArray(Iterators.filter(Iterators.forArray(BiomeGenBase.getBiomeGenArray()), Predicates.notNull()), BiomeGenBase.class);
		
		EntityRegistry.addSpawn(EntityMaxZombie.class, 100, 4, 4, EnumCreatureType.MONSTER, allBiomes);
		DungeonHooks.addDungeonMob("maxzombie", 500);

		EntityRegistry.removeSpawn(EntityZombie.class, EnumCreatureType.MONSTER, allBiomes);
		DungeonHooks.removeDungeonMob("Zombie");
		
		EntityRegistry.removeSpawn(EntityCreeper.class, EnumCreatureType.MONSTER, allBiomes);
		EntityRegistry.removeSpawn(EntitySkeleton.class, EnumCreatureType.MONSTER, allBiomes);
		DungeonHooks.removeDungeonMob("Skeleton");
		EntityRegistry.removeSpawn(EntityEnderman.class, EnumCreatureType.MONSTER, allBiomes);
		EntityRegistry.removeSpawn(EntitySpider.class, EnumCreatureType.MONSTER, allBiomes);
		DungeonHooks.removeDungeonMob("Spider");
		EntityRegistry.removeSpawn(EntitySlime.class, EnumCreatureType.MONSTER, allBiomes);
		EntityRegistry.removeSpawn(EntityWitch.class, EnumCreatureType.MONSTER, allBiomes);

	}

	public void postInit(FMLPostInitializationEvent e) {

	}

	public void registerModEntityWithEgg(Class parEntityClass,String parEntityName, int parEggColor, int parEggSpotsColor) {
		int entityID = EntityRegistry.findGlobalUniqueEntityId();
		EntityRegistry.registerModEntity(parEntityClass, parEntityName,	entityID, Main.instance, 80, 3, false);
//		registerSpawnEgg(parEntityName, parEggColor, parEggSpotsColor);
	}
}
