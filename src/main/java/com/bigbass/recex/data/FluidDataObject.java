package com.bigbass.recex.data;

public class FluidDataObject {
	
	/** amount */
	public int a;
	
	/** unlocalizedName */
	public String uN;
	
	/** localizedName */
	public String lN;
	
	public FluidDataObject(){
		
	}
	
	public FluidDataObject(int amount, String fluidName, String unlocalizedName){
		this.a = amount;
		this.uN = unlocalizedName;
		this.lN = fluidName;
	}
}
