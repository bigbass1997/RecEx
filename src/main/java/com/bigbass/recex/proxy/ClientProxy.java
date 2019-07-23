package com.bigbass.recex.proxy;

import com.bigbass.recex.KeyBindings;
import com.bigbass.recex.listeners.KeyInputEventHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e){
		super.preInit(e);
		
		FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());
	}
	
	public void init(FMLInitializationEvent e){
		super.init(e);
		
		KeyBindings.getInstance();
	}
}
