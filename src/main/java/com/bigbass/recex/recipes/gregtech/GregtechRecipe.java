package com.bigbass.recex.recipes.gregtech;

import java.util.ArrayList;
import java.util.List;

import com.bigbass.recex.recipes.ingredients.Fluid;
import com.bigbass.recex.recipes.ingredients.Item;

public class GregtechRecipe {
	
	/** enabled */
	public boolean en;
	/** duration */
	public int dur;
	/** EU/t */
	public int eut;
	/** itemInputs */
	public List<Item> iI;
	/** itemOutputs */
	public List<Item> iO;
	/** fluidInputs */
	public List<Fluid> fI;
	/** fluidOutputs */
	public List<Fluid> fO;
	
	public GregtechRecipe(){
		iI = new ArrayList<Item>();
		iO = new ArrayList<Item>();
		fI = new ArrayList<Fluid>();
		fO = new ArrayList<Fluid>();
	}
}
