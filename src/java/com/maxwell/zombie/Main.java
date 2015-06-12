package com.maxwell.zombie;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main {

    public static final String MODID = "zombie";
    public static final String MODNAME = "Zombie";
    public static final String VERSION = "1.0.0";
        
    @Instance
    public static Main instance = new Main();
    
    @SidedProxy(clientSide="com.maxwell.zombie.ClientProxy", serverSide="com.maxwell.zombie.ServerProxy")
    public static CommonProxy proxy;
        
     
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
//    	System.out.println("Called method: preInit");
    	proxy.preInit(e);
                
    }
        
    @EventHandler
    public void init(FMLInitializationEvent e) {
//    	System.out.println("Called method: init");
    	proxy.init(e);
                
    }
        
    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {                
//    	System.out.println("Called method: postInit");
    	proxy.postInit(e);
    }
}
