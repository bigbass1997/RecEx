package com.bigbass.recex.recipes;

import java.util.ArrayList;
import java.util.List;

import com.bigbass.recex.recipes.ingredients.Item;

public class ShapedRecipe {
	
	/** input items */
	public List<Item> iI;
	/** output item */
	public Item o;
	
	public ShapedRecipe(){
		iI = new ArrayList<Item>();
	}
}
