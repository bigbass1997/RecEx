package com.bigbass.recex.data;

public class ItemDataObject {
	
	/** amount */
	public int a;
	
	/** unlocalizedName */
	public String uN;
	
	/** localizedName */
	public String lN;
	
	public ItemDataObject(){
		
	}
	
	public ItemDataObject(int amount, String unlocalizedName, String displayName){
		this.a = amount;
		this.uN = unlocalizedName;
		this.lN = displayName;
	}
}
