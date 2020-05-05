package com.bigbass.recex.recipes;

import java.util.ArrayList;
import java.util.List;

public class ShapelessRecipe implements Recipe {
	
	/** input items */
	public List<Item> iI;
	/** output item */
	public Item o;
	
	public ShapelessRecipe(){
		iI = new ArrayList<Item>();
	}
}
