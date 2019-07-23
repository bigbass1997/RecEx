package com.bigbass.recex.listeners;

import com.bigbass.recex.KeyBindings;
import com.bigbass.recex.gui.GuiMain;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.Minecraft;

public class KeyInputEventHandler {
	
	private GuiMain guiInstance;
	
	public KeyInputEventHandler(){
		guiInstance = new GuiMain();
	}
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent e){
		if(KeyBindings.getInstance().binding.isPressed()){
			Minecraft.getMinecraft().displayGuiScreen(guiInstance);
		}
	}
}
