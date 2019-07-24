package com.bigbass.recex;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bigbass.recex.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = RecipeExporterMod.MODID, version = RecipeExporterMod.VERSION, acceptableRemoteVersions = "*")
public class RecipeExporterMod
{
	public static final String MODID = "RecEx";
	public static final String VERSION = "0.0.1";
	
	public static final Logger log = LogManager.getLogger("RecEx");
	
	@Mod.Instance(MODID)
	public static RecipeExporterMod instance;
	
	@SidedProxy(clientSide = "com.bigbass.recex.proxy.ClientProxy", serverSide = "com.bigbass.recex.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	public static File clientConfigDir;
	public static File modConfigDir;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e){
		clientConfigDir = e.getModConfigurationDirectory();
		modConfigDir = new File(clientConfigDir.getPath() + "/RecEx");
		if(!modConfigDir.exists()){
			modConfigDir.mkdirs();
		}
		proxy.preInit(e);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}
}
