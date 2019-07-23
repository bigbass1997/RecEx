package com.bigbass.recex;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings {
	
	private static KeyBindings instance;

	public KeyBinding binding;
	
	private KeyBindings(){
		binding = new KeyBinding("Open Exporter GUI", Keyboard.KEY_K, "Recipe Exporter");
		
		ClientRegistry.registerKeyBinding(binding);
	}
	
	public static KeyBindings getInstance(){
		if(instance == null){
			instance = new KeyBindings();
		}
		
		return instance;
	}
}
