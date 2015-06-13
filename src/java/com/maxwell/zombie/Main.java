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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.maxwell.zombie.entity.EntityMaxZombie;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main
{

	public static final String MODID = "zombie";
	public static final String MODNAME = "Zombie";
	public static final String VERSION = "1.0.0";

	@Instance
	public static Main instance = new Main();

	@SidedProxy(clientSide = "com.maxwell.zombie.ClientProxy", serverSide = "com.maxwell.zombie.ServerProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		// System.out.println("Called method: preInit");
		proxy.preInit(e);

	}

	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		// System.out.println("Called method: init");
		proxy.init(e);
		
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		// System.out.println("Called method: postInit");
		proxy.postInit(e);


	}
}
