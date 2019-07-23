package com.bigbass.recex.recipes;

public class Item {
	
	/** amount */
	public int a;
	
	/** unlocalizedName */
	public String uN;
	
	/** localizedName */
	public String lN;
	
	public Item(){
		
	}
	
	public Item(int amount, String unlocalizedName, String displayName){
		this.a = amount;
		this.uN = unlocalizedName;
		this.lN = displayName;
	}
}
