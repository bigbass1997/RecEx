package com.bigbass.recex.recipes.gregtech;

import java.util.ArrayList;
import java.util.List;

import com.bigbass.recex.recipes.ItemProgrammedCircuit;
import com.bigbass.recex.recipes.ingredients.Fluid;
import com.bigbass.recex.recipes.ingredients.Item;
import com.bigbass.recex.recipes.ingredients.ItemOreDict;

import gregtech.api.util.GT_LanguageManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeUtil {
	
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
			item.lN = stack.getDisplayName();
		} catch(Exception e){}
		
		return item;
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
			item.lN = stack.getDisplayName();
		} catch(Exception e1){
			try {
				item.lN = GT_LanguageManager.getTranslation(stack.getUnlocalizedName());
			} catch(Exception e2){}
		}
		
		if(item.uN != null && !item.uN.isEmpty() && item.uN.equalsIgnoreCase("gt.integrated_circuit")){ // Programmed Circuit
			item = new ItemProgrammedCircuit(item, stack.getItemDamage());
		}
		
		return item;
	}
	
	/**
	 * Might return null!
	 * 
	 * @param name
	 * @return
	 */
	public static ItemOreDict parseOreDictionary(String name){
		if(name == null || name.isEmpty()){
			return null;
		}
		
		List<Item> items = searchOreDictionary(name);
		if(items == null || items.isEmpty()){
			return null;
		}
		
		ItemOreDict item = new ItemOreDict();
		item.dns.add(name);
		item.ims = items;
		
		return item;
	}
	
	/**
	 * Might return null!
	 * 
	 * @param names
	 * @return
	 */
	public static ItemOreDict parseOreDictionary(String[] names){
		if(names == null || names.length == 0){
			return null;
		}
		
		ItemOreDict retItem = new ItemOreDict();
		for(String name : names){
			ItemOreDict tmpItem = parseOreDictionary(name);
			if(tmpItem != null){
				retItem.dns.addAll(tmpItem.dns);
				retItem.ims.addAll(tmpItem.ims);
			}
		}
		
		return retItem;
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
			fluid.lN = GT_LanguageManager.getTranslation(stack.getUnlocalizedName());
		} catch(Exception e1){
			try {
				fluid.lN = stack.getFluid().getName();
			} catch(Exception e2){
				try {
					fluid.lN = stack.getLocalizedName();
				} catch(Exception e3){}
			}
		}
		
		return fluid;
	}
	
	/**
	 * Retrieves all items which match a given OreDictionary name.
	 * 
	 * @param name OreDictionary name
	 * @return Collection of items retrieved from the OreDictionary
	 */
	public static List<Item> searchOreDictionary(String name){
		List<ItemStack> retrievedItemStacks = OreDictionary.getOres(name);
		List<Item> retrievedItems = new ArrayList<Item>();
		
		for(ItemStack stack : retrievedItemStacks){
			Item item = RecipeUtil.formatRegularItemStack(stack);
			retrievedItems.add(item);
		}
		return retrievedItems;
	}
}
