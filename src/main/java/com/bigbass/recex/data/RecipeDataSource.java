package com.bigbass.recex.data;

import java.util.ArrayList;

public class RecipeDataSource {
	
	public String src;
	public ArrayList<RecipeDataObject> recipes;
	
	public RecipeDataSource(){
		recipes = new ArrayList<RecipeDataObject>();
	}
}
