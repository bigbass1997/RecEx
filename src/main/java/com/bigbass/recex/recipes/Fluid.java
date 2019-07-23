package com.bigbass.recex.recipes;

public class Fluid {
	
	/** amount */
	public int a;
	
	/** unlocalizedName */
	public String uN;
	
	/** localizedName */
	public String lN;
	
	public Fluid(){
		
	}
	
	public Fluid(int amount, String unlocalizedName, String fluidName){
		this.a = amount;
		this.uN = unlocalizedName;
		this.lN = fluidName;
	}
}
