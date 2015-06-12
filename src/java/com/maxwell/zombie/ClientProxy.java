package com.maxwell.zombie;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.maxwell.zombie.client.render.blocks.BlockRenderRegister;
import com.maxwell.zombie.client.render.entity.RenderMaxZombie;
import com.maxwell.zombie.client.render.items.ItemRenderRegister;
import com.maxwell.zombie.entity.EntityMaxZombie;

public class ClientProxy extends CommonProxy{

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		// TODO Auto-generated method stub
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e) {
		// TODO Auto-generated method stub
		super.init(e);
		
		ItemRenderRegister.registerItemRenderer();
		BlockRenderRegister.registerBlockRenderer();
		
		RenderManager rm = Minecraft.getMinecraft().getRenderManager();
		RenderingRegistry.registerEntityRenderingHandler(EntityMaxZombie.class, new RenderMaxZombie(rm));
		


	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		// TODO Auto-generated method stub
		super.postInit(e);
	}

}
