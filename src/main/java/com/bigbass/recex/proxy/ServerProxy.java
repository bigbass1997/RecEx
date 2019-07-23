package com.bigbass.recex.proxy;

import com.bigbass.recex.RecipeExporterMod;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e){
		RecipeExporterMod.log.warn("This is a clientside only mod!");
	}
}
