package com.bigbass.recex.recipes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.bigbass.recex.RecipeExporterMod;
import com.bigbass.recex.recipes.gregtech.GregtechMachine;
import com.bigbass.recex.recipes.gregtech.GregtechRecipe;
import com.bigbass.recex.recipes.gregtech.RecipeUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidStack;

public class RecipeExporter {
	
	private static RecipeExporter instance;
	
	private RecipeExporter(){}
	
	public static RecipeExporter getInst(){
		if(instance == null){
			instance = new RecipeExporter();
		}
		
		return instance;
	}
	
	/**
	 * <p>Collects recipes into a master Hashtable (represents a JSON Object),
	 * then serializes it and saves it to a datetime-stamped file.</p>
	 * 
	 * <p>Recipes are stored in collections, often either List's or Hashtable's.
	 * The Gson library will serialize objects based on their public fields.
	 * The field name becomes the key, and the value is also serialized the same way.
	 * Lists are serialized as JSON arrays.</p>
	 * 
	 * <p>Schema for existing recipe sources should not be radically changed unless
	 * truly necessary. Adding additional data is acceptable however.</p>
	 */
	public void run(){
		Hashtable<String, Object> root = new Hashtable<String, Object>();
		
		List<Object> sources = new ArrayList<Object>();
		sources.add(getGregtechRecipes());
		sources.add(getShapedRecipes());
		sources.add(getShapelessRecipes());
		//TODO Support Oredicted Shaped and Shapeless Ore recipes
		
		root.put("sources", sources);
		
		Gson gson = (new GsonBuilder()).serializeNulls().create();
		try {
			saveData(gson.toJson(root));
		} catch(Exception e){
			e.printStackTrace();
			RecipeExporterMod.log.error("Recipes failed to export!");
		}
	}
	
	/**
	 * <p>Unlike vanilla recipes, the current schema here groups recipes from each machine together.
	 * This is a minor file size improvement. Rather than specifying the machine's name in every recipe,
	 * the machine name is only listed once for the entire file.</p>
	 * 
	 * <p>This format does not impede the process of loading the recipes into NEP.</p>
	 */
	private Object getGregtechRecipes(){
		Hashtable<String, Object> data = new Hashtable<String, Object>();
		
		data.put("type", "gregtech");
		
		List<GregtechMachine> machines = new ArrayList<GregtechMachine>();
		for(GT_Recipe_Map map : GT_Recipe_Map.sMappings){
			GregtechMachine mach = new GregtechMachine();
			
			// machine name retrieval
			mach.n = GT_LanguageManager.getTranslation(map.mUnlocalizedName);
			if(mach.n == null || mach.n.isEmpty()){
				mach.n = map.mUnlocalizedName;
			}
			
			for(GT_Recipe rec : map.mRecipeList){
				GregtechRecipe gtr = new GregtechRecipe();
				gtr.en = rec.mEnabled;
				gtr.dur = rec.mDuration;
				gtr.eut = rec.mEUt;
				
				// item inputs
				for(ItemStack stack : rec.mInputs){
					Item item = RecipeUtil.formatGregtechItemStack(stack);
					
					if(item == null){
						continue;
					}
					
					gtr.iI.add(item);
				}
				
				// item outputs
				for(ItemStack stack : rec.mOutputs){
					Item item = RecipeUtil.formatGregtechItemStack(stack);
					
					if(item == null){
						continue;
					}
					
					gtr.iO.add(item);
				}
				
				// fluid inputs
				for(FluidStack stack : rec.mFluidInputs){
					Fluid fluid = RecipeUtil.formatGregtechFluidStack(stack);
					
					if(fluid == null){
						continue;
					}
					
					gtr.fI.add(fluid);
				}
				
				// fluid outputs
				for(FluidStack stack : rec.mFluidOutputs){
					Fluid fluid = RecipeUtil.formatGregtechFluidStack(stack);
					
					if(fluid == null){
						continue;
					}
					
					gtr.fO.add(fluid);
				}
				
				mach.recs.add(gtr);
			}
			machines.add(mach);
		}
		
		data.put("machines", machines);
		
		return data;
	}
	
	private Object getShapedRecipes(){
		Hashtable<String, Object> data = new Hashtable<String, Object>();
		
		data.put("type", "shaped");
		
		List<ShapedRecipe> retRecipes = new ArrayList<ShapedRecipe>();
		List<?> recipes = CraftingManager.getInstance().getRecipeList();
		for(Object obj : recipes){
			if(obj instanceof ShapedRecipes){
				ShapedRecipes original = (ShapedRecipes) obj;
				ShapedRecipe rec = new ShapedRecipe();
				
				for(ItemStack stack : original.recipeItems){
					Item item = RecipeUtil.formatRegularItemStack(stack);
					rec.iI.add(item);
				}
				
				rec.o = RecipeUtil.formatRegularItemStack(original.getRecipeOutput());
				
				retRecipes.add(rec);
			}
		}
		data.put("recipes", retRecipes);
		
		return data;
	}

	private Object getShapelessRecipes(){
		Hashtable<String, Object> data = new Hashtable<String, Object>();
		
		data.put("type", "shapeless");
		
		List<ShapelessRecipe> retRecipes = new ArrayList<ShapelessRecipe>();
		List<?> recipes = CraftingManager.getInstance().getRecipeList();
		for(Object obj : recipes){
			if(obj instanceof ShapelessRecipes){
				ShapelessRecipes original = (ShapelessRecipes) obj;
				ShapelessRecipe rec = new ShapelessRecipe();
				
				for(Object stack : original.recipeItems){
					Item item = null;
					if(stack instanceof ItemStack){
						item = RecipeUtil.formatRegularItemStack((ItemStack) stack);
					} else if(stack instanceof net.minecraft.item.Item){
						item = RecipeUtil.formatRegularItemStack(new ItemStack((net.minecraft.item.Item) stack));
					}
					
					rec.iI.add(item);
				}
				
				rec.o = RecipeUtil.formatRegularItemStack(original.getRecipeOutput());
				
				retRecipes.add(rec);
			}
		}
		data.put("recipes", retRecipes);
		
		return data;
	}
	
	private void saveData(String json){
		try {
			FileWriter writer = new FileWriter(getSaveFile());
			writer.write(json);
			writer.close();
			
			RecipeExporterMod.log.info("Recipes have been exported.");
		} catch (IOException e) {
			e.printStackTrace();
			RecipeExporterMod.log.error("Recipes failed to save!");
		} catch (NullPointerException e) {
			e.printStackTrace();
			RecipeExporterMod.log.error("Recipes failed to save!");
		}
	}
	
	private File getSaveFile(){
		String dateTime = ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("uuuu-MM-dd_HH-mm-ss"));
		File file = new File(RecipeExporterMod.clientConfigDir.getParent() + "/RecEx-Records/" + dateTime + ".json");
		if(!file.exists()){
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return file;
	}
}
