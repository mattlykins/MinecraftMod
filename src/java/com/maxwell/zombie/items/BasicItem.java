package com.maxwell.zombie.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BasicItem extends Item{
	
	public BasicItem(String unlocalizedName){		
		super();
		
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		
		
	}
}
