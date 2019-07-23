package com.bigbass.recex.gui;

import com.bigbass.recex.RecipeExporterMod;
import com.bigbass.recex.graphics.Color;
import com.bigbass.recex.graphics.GraphicsRender;
import com.bigbass.recex.graphics.GraphicsRender.FillType;
import com.bigbass.recex.recipes.RecipeExporter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

@SideOnly(Side.CLIENT)
public class GuiMain extends GuiScreen {
	
	private final Color fontColor = new Color(0xFFFFFFFF);
	private final Color background = new Color(0x000000FF);
	private GuiButton exportButton;
	
	public GuiMain(){
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initGui(){
		exportButton = new GuiButton(0, 50, 50, 200, 20, "Export!");
		
		this.buttonList.add(exportButton);
	}
	
	@Override
	public void drawScreen(int mx, int my, float partTicks){
		GraphicsRender.rect(0, 0, width, height, background, FillType.FILLED);
		
		this.fontRendererObj.drawString("Recipe Exporter",
				(this.width / 2) - (this.fontRendererObj.getStringWidth("Recipe Exporter") / 2), 6,
				fontColor.toARGB()
		);
		
		super.drawScreen(mx, my, partTicks);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode){
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	protected void mouseClicked(int mx, int my, int button){
		super.mouseClicked(mx, my, button);
	}
	
	@Override
	protected void actionPerformed(GuiButton button){
		if(button.equals(exportButton)){
			RecipeExporterMod.log.info("Export Button Pressed!");
			
			RecipeExporter.getInst().run();
		}
	}
	
	@Override
	public boolean doesGuiPauseGame(){
	    return false;
	}
	
	@Override
	public void drawDefaultBackground(){
		
	}
}
