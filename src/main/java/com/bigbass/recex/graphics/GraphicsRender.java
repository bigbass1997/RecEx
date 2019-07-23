package com.bigbass.recex.graphics;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;

public class GraphicsRender {
	
	public static enum FillType {
		FILLED,
		LINE
	}
	
	@Deprecated
	public static void text(double x, double y, String text, int size, Minecraft mc){
		//FontRenderer font = mc.fontRenderer;
		//TODO Write the rest of this method. Will require scaling based on requested size.
	}
	
	public static void rect(double x1, double y1, double x2, double y2, Color color, FillType type){
		Tessellator tessellator = Tessellator.instance;
        
		double j1;

        if (x1 < x2)
        {
            j1 = x1;
            x1 = x2;
            x2 = j1;
        }

        if (y1 < y2)
        {
            j1 = y1;
            y1 = y2;
            y2 = j1;
        }
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        switch(type){
        case FILLED:
        	tessellator.startDrawing(GL11.GL_QUADS);
        	
        	GL11.glColor4f(color.r, color.g, color.b, color.a);
            tessellator.addVertex(x1, y1, 0d);
            tessellator.addVertex(x1, y2, 0d);
            tessellator.addVertex(x2, y2, 0d);
            tessellator.addVertex(x2, y1, 0d);
        	break;
        case LINE:
        	tessellator.startDrawing(GL11.GL_LINES);
            
            GL11.glColor4f(color.r, color.g, color.b, color.a);
            tessellator.addVertex(x1, y1, 0d);
            tessellator.addVertex(x1, y2, 0d);
            
            tessellator.addVertex(x1, y2, 0d);
            tessellator.addVertex(x2, y2, 0d);
            
            tessellator.addVertex(x2, y2, 0d);
            tessellator.addVertex(x2, y1, 0d);

            tessellator.addVertex(x2, y1, 0d);
            tessellator.addVertex(x1, y1, 0d);
        	break;
        }
        
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void line(double x1, double y1, double x2, double y2, Color color){
		Tessellator tessellator = Tessellator.instance;
		
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        tessellator.startDrawing(GL11.GL_LINES);

        GL11.glColor4f(color.r, color.g, color.b, color.a);
        tessellator.addVertex(x1, y1, 0d);
        tessellator.addVertex(x2, y2, 0d);
        
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void line(double x1, double y1, double x2, double y2, Color color1, Color color2){
		Tessellator tessellator = Tessellator.instance;
        
		GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        tessellator.startDrawing(GL11.GL_LINES);
        
        tessellator.setColorRGBA_F(color1.r, color1.g, color1.b, color1.a);
        tessellator.addVertex(x1, y1, 0d);
        tessellator.setColorRGBA_F(color2.r, color2.g, color2.b, color2.a);
        tessellator.addVertex(x2, y2, 0d);
        
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
	}
}
