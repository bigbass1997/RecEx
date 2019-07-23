package com.bigbass.recex.data;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpw.mods.fml.common.registry.GameData;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fluids.FluidStack;

public class RecipeDataController {
	
	private ArrayList<RecipeDataSource> recipeSources;
	
	private boolean isPopulated = false;
	
	public RecipeDataController(){
		recipeSources = new ArrayList<RecipeDataSource>();
	}
	
	public void populate(){
		if(isPopulated){
			return;
		}
		
		// GregTech
		RecipeDataSource gregtech = new RecipeDataSource();
		gregtech.src = "gregtech";
		
		/*addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sAlloySmelterRecipes, "AlloySmelter");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sArcFurnaceRecipes, "ArcFurnace");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sAssemblerRecipes, "Assembler");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sAutoclaveRecipes, "Autoclave");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sBenderRecipes, "Bender");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sBlastRecipes, "BlastFurnace");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sBoxinatorRecipes, "Boxinator");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sBrewingRecipes, "Brewer");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sCannerRecipes, "Canner");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes, "Centrifuge");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sChemicalBathRecipes, "ChemicalBath");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sChemicalRecipes, "ChemicalReactor");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sCNCRecipes, "CNCMachine");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sCompressorRecipes, "Compressor");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sCrakingRecipes, "OilCracker");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sCutterRecipes, "CuttingMachine");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sDistillationRecipes, "DistillationTower");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sDistilleryRecipes, "Distillery");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sElectrolyzerRecipes, "Electrolyzer");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sElectroMagneticSeparatorRecipes, "ElectromagneticSeparator");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sExtractorRecipes, "Extractor");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sExtruderRecipes, "Extruder");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sFermentingRecipes, "Fermenter");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes, "FluidCanner");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes, "FluidExtractor");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sFluidHeaterRecipes, "FluidHeater");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sFluidSolidficationRecipes, "FluidSolidifier");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sFurnaceRecipes, "Furnace");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sFusionRecipes, "FusionReactor");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sHammerRecipes, "ForgeHammer");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sImplosionRecipes, "ImplosionCompressor");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sLaserEngraverRecipes, "LaserEngraver");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sLatheRecipes, "Lathe");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sMaceratorRecipes, "Macerator");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sMicrowaveRecipes, "Microwave");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sMixerRecipes, "Mixer");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sOreWasherRecipes, "OreWasher");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sPlasmaArcFurnaceRecipes, "PlasmaArcFurnace");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sPolarizerRecipes, "Polarizer");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sPressRecipes, "FormingPress");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sPrinterRecipes, "Printer");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sPyrolyseRecipes, "PyrolyseOven");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sRecyclerRecipes, "Recycler");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sSifterRecipes, "Sifter");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sSlicerRecipes, "Slicer");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sThermalCentrifugeRecipes, "ThermalCentrifuge");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sUnboxinatorRecipes, "Unboxinator");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sVacuumRecipes, "VacuumFreezer");
		addRecipesFromMap(gregtech, GT_Recipe.GT_Recipe_Map.sWiremillRecipes, "Wiremill");*/
		
		for(GT_Recipe_Map map : GT_Recipe.GT_Recipe_Map.sMappings){
			addRecipesFromMap(gregtech, map, GT_LanguageManager.getTranslation(map.mUnlocalizedName));
		}
		
		recipeSources.add(gregtech);
		
		
		CraftingManager.getInstance().getRecipeList();
		GameData.getItemRegistry().getObjectById(0);
		
		
		isPopulated = true;
	}
	
	private void addRecipesFromMap(RecipeDataSource source, GT_Recipe_Map map, String machineName){
		for(GT_Recipe rec : map.mRecipeList){
			RecipeDataObject data = new RecipeDataObject();
			data.en = rec.mEnabled;
			data.dur = rec.mDuration;
			data.eut = rec.mEUt;
			data.mN = machineName;
			populateRecipeArrays(data, rec);
			
			source.recipes.add(data);
		}
	}
	
	private void populateRecipeArrays(RecipeDataObject data, GT_Recipe rec){
		for(ItemStack stack : rec.mInputs){
			createAndAddItemDataObject(data.iI, stack);
		}
		for(ItemStack stack : rec.mOutputs){
			createAndAddItemDataObject(data.iO, stack);
		}
		
		for(FluidStack stack : rec.mFluidInputs){
			createAndAddFluidDataObject(data.fI, stack);
		}
		for(FluidStack stack : rec.mFluidOutputs){
			createAndAddFluidDataObject(data.fO, stack);
		}
	}
	
	private void createAndAddItemDataObject(ArrayList<ItemDataObject> data, ItemStack stack){
		if(stack == null){
			//data.add(new ItemDataObject(-1, null, null));
			return;
		}
		
		try {
			int amount = stack.stackSize;
			String unlocal;
			String display;
			
			try {
				unlocal = stack.getUnlocalizedName();
			} catch(Exception e) {
				unlocal = null;
			}
			
			try {
				display = stack.getDisplayName();
			} catch(Exception e) {
				display = null;
			}
			
			data.add(new ItemDataObject(amount, unlocal, display));
		} catch(Exception e){}
	}

	private void createAndAddFluidDataObject(ArrayList<FluidDataObject> data, FluidStack stack){
		if(stack == null){
			data.add(new FluidDataObject(-1, null, null));
		}
		

		try {
			int amount = stack.amount;
			String fluidName;
			String unlocal;
			
			try {
				fluidName = stack.getFluid().getName();
			} catch(Exception e) {
				fluidName = null;
			}
			
			try {
				unlocal = stack.getUnlocalizedName();
			} catch(Exception e) {
				unlocal = null;
			}
			
			data.add(new FluidDataObject(amount, fluidName, unlocal));
		} catch(Exception e){}
	}
	
	public String formatToJson(){
		Gson gson = (new GsonBuilder()).serializeNulls().create();
		try {
			return gson.toJson(recipeSources);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String formatToJsonPretty(){
		Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
		
		try {
			return gson.toJson(recipeSources);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
