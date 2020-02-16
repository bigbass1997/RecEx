package com.bigbass.recex.recipes.ingredients;

import java.util.ArrayList;
import java.util.List;

public class ItemOreDict implements IItem {
	
	/** dictionary name(s) */
	public List<String> dns;
	
	/** items which match the dictionary name */
	public List<Item> ims;
    
    public ItemOreDict(){
    	dns = new ArrayList<String>();
    	ims = new ArrayList<Item>();
    }
}
