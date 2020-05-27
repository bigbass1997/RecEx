package com.bigbass.recex.recipes.renderer;

import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineHelper;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngWriter;
import com.bigbass.recex.RecipeExporterMod;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.INVENTORY;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.INVENTORY_BLOCK;

public class IconRenderer {
    private static IconRenderer INSTANCE;

    public static IconRenderer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IconRenderer();
        }
        return INSTANCE;
    }

    private int width = 64, height = 64;

    private int frameBufferObject;
    private int renderBufferObject;
    private int depthBufferObject;

    private boolean bufferInitialized = false;

    public void init() {
        frameBufferObject = GL30.glGenFramebuffers();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferObject);

        renderBufferObject = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, renderBufferObject);
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_RGBA8, width, height);
        GL30.glFramebufferRenderbuffer(GL30.GL_DRAW_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_RENDERBUFFER, renderBufferObject);

        depthBufferObject = GL30.glGenRenderbuffers();
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBufferObject);
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH_COMPONENT32F, width, height);
        GL30.glFramebufferRenderbuffer(GL30.GL_DRAW_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, depthBufferObject);

        if (GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) == GL30.GL_FRAMEBUFFER_COMPLETE) {
            bufferInitialized = true;
        } else {
            throw new RuntimeException("failed to create frame buffer.");
        }
        // reset binding
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    public void printItemStack(ItemStack itemStack, String unlocalizedName) {
        checkFrameBuffer();
        File output = getPngFile(unlocalizedName);
        if (output == null) return;
        before();

        // draw icon
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        renderItemAndEffectIntoGUI(textureManager, itemStack);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        printPng(output);
        after();
    }

    public void printFluidStack(FluidStack fluidStack, String unlocalizedName) {
        checkFrameBuffer();
        File output = getPngFile(unlocalizedName);
        if (output == null) return;
        before();

        // draw icon
        renderFluidIcon(fluidStack);

        printPng(output);
        after();
    }

    private void checkFrameBuffer() {
        if (!bufferInitialized) {
            throw new RuntimeException("frame buffer not initialized.");
        }
    }

    private File getPngFile(String unlocalizedName) {
        String encodedUnlocalizedName = Base64.getEncoder().encodeToString(unlocalizedName.getBytes());
        File output = new File(RecipeExporterMod.clientConfigDir.getParent() + "/RecEx-Icons/" + encodedUnlocalizedName + ".png");
        try {
            if(!output.exists()){
                boolean result = output.getParentFile().mkdirs();
                if (!result) {
                    throw new IOException("failed to create icon folder.");
                }
            }
            if (output.exists()) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    private void before() {
        GL11.glPushMatrix();

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, frameBufferObject);
        GL11.glPushAttrib(GL11.GL_VIEWPORT_BIT);
        GL11.glViewport(0, 0, width, height);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0d, 16f, 16f, 0d, -1000d, 3000d);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();

        GL11.glClearColor(0f, 0f, 0f, 0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    private void after() {
        GL11.glPopAttrib();
        GL11.glPopMatrix();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    private void printPng(File output) {
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

        GL11.glReadBuffer(GL30.GL_COLOR_ATTACHMENT0);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

        int[][][] pixels = new int[width][height][4];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                pixels[height - h - 1][w][0] = (buffer.get()) & 0xff;
                pixels[height - h - 1][w][1] = (buffer.get()) & 0xff;
                pixels[height - h - 1][w][2] = (buffer.get()) & 0xff;
                pixels[height - h - 1][w][3] = (buffer.get()) & 0xff;
            }
        }

        ImageInfo info = new ImageInfo(width, height, 8, true);
        try {
            PngWriter pngWriter = new PngWriter(output, info);
            ImageLineInt iLine = new ImageLineInt(info);
            for (int row = 0; row < info.rows; row++) {
                for (int col = 0; col < info.cols; col++) {
                    int r = pixels[row][col][0];
                    int g = pixels[row][col][1];
                    int b = pixels[row][col][2];
                    int a = pixels[row][col][3];
                    ImageLineHelper.setPixelRGBA8(iLine, col, r, g, b, a);
                }
                pngWriter.writeRow(iLine);
            }
            pngWriter.end();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enableGUIStandardItemLighting() {
        GL11.glPushMatrix();
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
    }

    private RenderBlocks renderBlocks = RenderBlocks.getInstance();
    private boolean renderWithColor = true;
    private float zLevel = 0f;

    // Original source : RenderItem
    private void renderItemAndEffectIntoGUI(TextureManager textureManager, final ItemStack itemStack) {
        if (!renderInventoryItem(renderBlocks, textureManager, itemStack, renderWithColor, zLevel)) {
            renderItemIntoGUI(textureManager, itemStack);
        }
    }

    // Original source : ForgeHooksClient
    private boolean renderInventoryItem(RenderBlocks renderBlocks, TextureManager engine, ItemStack item, boolean inColor, float zLevel) {
        float x = 0f, y = 0f;

        if (item.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            item.setItemDamage(0);
        }

        IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(item, INVENTORY);
        if (customRenderer == null) {
            return false;
        }

        engine.bindTexture(item.getItemSpriteNumber() == 0 ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture);
        if (customRenderer.shouldUseRenderHelper(INVENTORY, item, INVENTORY_BLOCK)) {
            GL11.glPushMatrix();
            GL11.glTranslatef(x - 2, y + 3, -3.0F + zLevel);
            GL11.glScalef(10F, 10F, 10F);
            GL11.glTranslatef(1.0F, 0.5F, 1.0F);
            GL11.glScalef(1.0F, 1.0F, -1F);
            GL11.glRotatef(210F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);

            if(inColor) {
                int color = item.getItem().getColorFromItemStack(item, 0);
                float r = (float)(color >> 16 & 0xff) / 255F;
                float g = (float)(color >> 8 & 0xff) / 255F;
                float b = (float)(color & 0xff) / 255F;
                GL11.glColor4f(r, g, b, 1.0F);
            }

            RenderHelper.disableStandardItemLighting();
            enableGUIStandardItemLighting();
            GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
            renderBlocks.useInventoryTint = inColor;
            customRenderer.renderItem(INVENTORY, item, renderBlocks);
            renderBlocks.useInventoryTint = true;
            RenderHelper.enableStandardItemLighting();
            GL11.glPopMatrix();
        } else {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPushMatrix();
            GL11.glTranslatef(x, y, -3.0F + zLevel);

            if (inColor) {
                int color = item.getItem().getColorFromItemStack(item, 0);
                float r = (float)(color >> 16 & 255) / 255.0F;
                float g = (float)(color >> 8 & 255) / 255.0F;
                float b = (float)(color & 255) / 255.0F;
                GL11.glColor4f(r, g, b, 1.0F);
            }

            customRenderer.renderItem(INVENTORY, item, renderBlocks);
            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_LIGHTING);
        }

        return true;
    }

    // Original source : RenderItem
    private void renderItemIntoGUI(TextureManager textureManager, ItemStack itemStack) {
        int x = 0, y = 0;

        int itemDamage = itemStack.getItemDamage();
        if (itemDamage == OreDictionary.WILDCARD_VALUE) {
            itemStack.setItemDamage(0);
            itemDamage = 0;
        }

        IIcon iconIndex = itemStack.getIconIndex();
        int itemStackColor;

        // render block
        if (itemStack.getItemSpriteNumber() == 0 && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemStack.getItem()).getRenderType())) {
            textureManager.bindTexture(TextureMap.locationBlocksTexture);
            Block block = Block.getBlockFromItem(itemStack.getItem());
            GL11.glEnable(GL11.GL_ALPHA_TEST);

            if (block.getRenderBlockPass() != 0) {
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            } else {
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.5F);
                GL11.glDisable(GL11.GL_BLEND);
            }

            GL11.glPushMatrix();
            GL11.glTranslatef((float)(x - 2), (float)(y + 3), -3.0F + zLevel);
            GL11.glScalef(10.0F, 10.0F, 10.0F);
            GL11.glTranslatef(1.0F, 0.5F, 1.0F);
            GL11.glScalef(1.0F, 1.0F, -1.0F);
            GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            itemStackColor = itemStack.getItem().getColorFromItemStack(itemStack, 0);
            float r = (float)(itemStackColor >> 16 & 255) / 255.0F;
            float g = (float)(itemStackColor >> 8 & 255) / 255.0F;
            float b = (float)(itemStackColor & 255) / 255.0F;

            if (this.renderWithColor) {
                GL11.glColor4f(r, g, b, 1.0F);
            }

            RenderHelper.disableStandardItemLighting();
            enableGUIStandardItemLighting();
            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            renderBlocks.useInventoryTint = this.renderWithColor;
            renderBlocks.renderBlockAsItem(block, itemDamage, 1.0F);
            renderBlocks.useInventoryTint = true;
            RenderHelper.enableStandardItemLighting();

            if (block.getRenderBlockPass() == 0) {
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            }

            GL11.glPopMatrix();
        } else if (itemStack.getItem().requiresMultipleRenderPasses()) {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            textureManager.bindTexture(TextureMap.locationItemsTexture);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(0, 0, 0, 0);
            GL11.glColorMask(false, false, false, true);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.setColorOpaque_I(-1);
            tessellator.addVertex((x - 2), (y + 18), zLevel);
            tessellator.addVertex((x + 18), (y + 18), zLevel);
            tessellator.addVertex((x + 18), (y - 2), zLevel);
            tessellator.addVertex((x - 2), (y - 2), zLevel);
            tessellator.draw();
            GL11.glColorMask(true, true, true, true);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);

            Item item = itemStack.getItem();
            for (itemStackColor = 0; itemStackColor < item.getRenderPasses(itemDamage); ++itemStackColor) {
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                textureManager.bindTexture(item.getSpriteNumber() == 0 ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture);
                IIcon iicon = item.getIcon(itemStack, itemStackColor);
                int i1 = itemStack.getItem().getColorFromItemStack(itemStack, itemStackColor);
                float r = (float)(i1 >> 16 & 255) / 255.0F;
                float g = (float)(i1 >> 8 & 255) / 255.0F;
                float b = (float)(i1 & 255) / 255.0F;

                if (this.renderWithColor) {
                    GL11.glColor4f(r, g, b, 1.0F);
                }

                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_ALPHA_TEST);

                this.renderIcon(x, y, iicon);

                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_LIGHTING);
            }

            GL11.glEnable(GL11.GL_LIGHTING);
        } else {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            ResourceLocation resourcelocation = textureManager.getResourceLocation(itemStack.getItemSpriteNumber());
            textureManager.bindTexture(resourcelocation);

            if (iconIndex == null) {
                iconIndex = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(resourcelocation)).getAtlasSprite("missingno");
            }

            itemStackColor = itemStack.getItem().getColorFromItemStack(itemStack, 0);
            float r = (float)(itemStackColor >> 16 & 255) / 255.0F;
            float g = (float)(itemStackColor >> 8 & 255) / 255.0F;
            float b = (float)(itemStackColor & 255) / 255.0F;

            if (this.renderWithColor) {
                GL11.glColor4f(r, g, b, 1.0F);
            }

            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);

            this.renderIcon(x, y, iconIndex);

            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);

            GL11.glEnable(GL11.GL_LIGHTING);
        }

        GL11.glEnable(GL11.GL_CULL_FACE);
    }

    private void renderFluidIcon(FluidStack fluidStack) {
        int x = 0, y = 0;

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        ResourceLocation resourcelocation = textureManager.getResourceLocation(fluidStack.getFluid().getSpriteNumber());
        textureManager.bindTexture(resourcelocation);

        IIcon iconIndex = fluidStack.getFluid().getIcon(fluidStack);
        if (iconIndex == null) {
            iconIndex = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(resourcelocation)).getAtlasSprite("missingno");
        }

        int fluidStackColor = fluidStack.getFluid().getColor(fluidStack);
        float r = (float)(fluidStackColor >> 16 & 255) / 255.0F;
        float g = (float)(fluidStackColor >> 8 & 255) / 255.0F;
        float b = (float)(fluidStackColor & 255) / 255.0F;

        if (this.renderWithColor) {
            GL11.glColor4f(r, g, b, 1.0F);
        }

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);

        this.renderIcon(x, y, iconIndex);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glEnable(GL11.GL_LIGHTING);
    }

    private void renderIcon(int x, int y, IIcon icon) {
        int width = 16, height = 16;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, (y + height), zLevel, icon.getMinU(), icon.getMaxV());
        tessellator.addVertexWithUV((x + width), (y + height), zLevel, icon.getMaxU(), icon.getMaxV());
        tessellator.addVertexWithUV((x + width), y, zLevel, icon.getMaxU(), icon.getMinV());
        tessellator.addVertexWithUV(x, y, zLevel, icon.getMinU(), icon.getMinV());
        tessellator.draw();
    }

    public void dispose() {
        bufferInitialized = false;
        GL30.glDeleteFramebuffers(frameBufferObject);
        GL30.glDeleteRenderbuffers(renderBufferObject);
        GL30.glDeleteRenderbuffers(depthBufferObject);
    }
}
