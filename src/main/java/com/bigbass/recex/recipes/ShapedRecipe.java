package com.bigbass.recex.recipes;

import java.util.ArrayList;
import java.util.List;

public class ShapedRecipe implements Recipe {
	
	/** input items */
	public List<Item> iI;
	/** output item */
	public Item o;
	
	public ShapedRecipe(){
		iI = new ArrayList<Item>();
	}
}
