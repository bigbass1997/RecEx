package com.bigbass.recex.recipes;

import java.util.ArrayList;
import java.util.List;

import com.bigbass.recex.recipes.ingredients.IItem;
import com.bigbass.recex.recipes.ingredients.Item;

public class OreDictShapedRecipe {
	
	/** input items */
	public List<IItem> iI;
	/** output item */
	public Item o;
	
	public OreDictShapedRecipe(){
		iI = new ArrayList<IItem>();
	}
}
