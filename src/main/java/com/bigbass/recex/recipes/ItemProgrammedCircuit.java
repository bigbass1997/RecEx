package com.bigbass.recex.recipes;

public class ItemProgrammedCircuit extends Item {
	
	/** circuit config */
	public int cfg;
	
	public ItemProgrammedCircuit(){
		
	}
	
	public ItemProgrammedCircuit(Item item, int cfg){
		super(item.a, item.uN, item.lN);
		
		this.cfg = cfg;
	}
}
