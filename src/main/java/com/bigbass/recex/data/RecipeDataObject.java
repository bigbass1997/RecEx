package com.bigbass.recex.data;

import java.util.ArrayList;

public class RecipeDataObject {
	
	/** enabled */
	public boolean en;
	/** duration */
	public int dur;
	/** EU/t */
	public int eut;
	/** machineName */
	public String mN;
	/** itemInputs */
	public ArrayList<ItemDataObject> iI;
	/** itemOutputs */
	public ArrayList<ItemDataObject> iO;
	/** fluidInputs */
	public ArrayList<FluidDataObject> fI;
	/** fluidOutputs */
	public ArrayList<FluidDataObject> fO;
	
	public RecipeDataObject(){
		iI = new ArrayList<ItemDataObject>();
		iO = new ArrayList<ItemDataObject>();
		fI = new ArrayList<FluidDataObject>();
		fO = new ArrayList<FluidDataObject>();
	}
}
