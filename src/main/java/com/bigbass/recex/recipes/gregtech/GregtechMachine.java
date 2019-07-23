package com.bigbass.recex.recipes.gregtech;

import java.util.ArrayList;
import java.util.List;

public class GregtechMachine {
	
	/** machine name */
	public String n;
	/** recipes */
	public List<GregtechRecipe> recs;
	
	public GregtechMachine(){
		recs = new ArrayList<GregtechRecipe>();
	}
}
