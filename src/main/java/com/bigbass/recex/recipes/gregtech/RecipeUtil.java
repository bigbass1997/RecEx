package com.bigbass.recex.recipes.gregtech;

import com.bigbass.recex.recipes.Fluid;
import com.bigbass.recex.recipes.Item;
import com.bigbass.recex.recipes.ItemProgrammedCircuit;
import com.bigbass.recex.recipes.renderer.IconRenderer;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.regex.Pattern;

public class RecipeUtil {

	private static Pattern pattern = Pattern.compile("(?i)" + '\u00a7' + "[0-9A-FK-OR]");
	
	public static Item formatRegularItemStack(ItemStack stack){
		if(stack == null){
			return null;
		}
		
		Item item = new Item();
		
		item.a = stack.stackSize;
		try {
			item.uN = stack.getUnlocalizedName();
		} catch(Exception e){}
		try {
			item.lN = pattern.matcher(stack.getDisplayName()).replaceAll("");
		} catch(Exception e){}

		IconRenderer.getInstance().printItemStack(stack, item.uN);
		return item;
	}

	public static Fluid formatRegularFluidStack(FluidStack stack) {
		if (stack == null) {
			return null;
		}

		Fluid fluid = new Fluid();

		fluid.a = stack.amount;
		try {
			fluid.uN = stack.getUnlocalizedName();
		} catch (Exception e) {}
		try {
			fluid.lN = pattern.matcher(stack.getLocalizedName()).replaceAll("");
		} catch (Exception e) {}

		IconRenderer.getInstance().printFluidStack(stack, fluid.uN);
		return fluid;
	}
	
	public static Item formatGregtechItemStack(ItemStack stack){
		if(stack == null){
			return null;
		}
		
		Item item = new Item();
		
		item.a = stack.stackSize;
		try {
			item.uN = stack.getUnlocalizedName();
		} catch(Exception e){}
		try {
			item.lN = pattern.matcher(stack.getDisplayName()).replaceAll("");
		} catch(Exception e1){
			try {
				item.lN = pattern.matcher(GT_LanguageManager.getTranslation(stack.getUnlocalizedName())).replaceAll("");
			} catch(Exception e2){}
		}
		
		if(item.uN != null && !item.uN.isEmpty() && item.uN.equalsIgnoreCase("gt.integrated_circuit")){ // Programmed Circuit
			item = new ItemProgrammedCircuit(item, stack.getItemDamage());
		}

		IconRenderer.getInstance().printItemStack(stack, item.uN);
		return item;
	}
	
	public static Fluid formatGregtechFluidStack(FluidStack stack){
		if(stack == null){
			return null;
		}
		
		Fluid fluid = new Fluid();
		
		fluid.a = stack.amount;
		try {
			fluid.uN = stack.getUnlocalizedName();
		} catch(Exception e){}
		try {
			fluid.lN = pattern.matcher(GT_LanguageManager.getTranslation(stack.getUnlocalizedName())).replaceAll("");
		} catch(Exception e1){
			try {
				fluid.lN = pattern.matcher(stack.getFluid().getName()).replaceAll("");
			} catch(Exception e2){
				try {
					fluid.lN = pattern.matcher(stack.getLocalizedName()).replaceAll("");
				} catch(Exception e3){}
			}
		}

		IconRenderer.getInstance().printFluidStack(stack, fluid.uN);
		return fluid;
	}
}
