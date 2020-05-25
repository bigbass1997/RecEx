package com.bigbass.recex.recipes;

import com.bigbass.recex.RecipeExporterMod;
import com.bigbass.recex.recipes.exporters.ForestryRecipeExporter;
import com.bigbass.recex.recipes.gregtech.GregtechRecipe;
import com.bigbass.recex.recipes.gregtech.RecipeUtil;
import com.bigbass.recex.recipes.renderer.IconRenderer;
import com.bigbass.recex.recipes.serializers.ItemListSerializer;
import com.bigbass.recex.recipes.serializers.MachineSerializer;
import com.bigbass.recex.recipes.serializers.ModSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

		IconRenderer.getInstance().init();
		
		List<Object> sources = new ArrayList<Object>();
		sources.add(getGregtechRecipes());
		sources.add(new ForestryRecipeExporter().getForestryRecipes());
		sources.add(getShapedRecipes());
		sources.add(getShapelessRecipes());
		sources.add(getOreDictShapedRecipes());
		sources.add(getOreDictShapelessRecipes());
		sources.add(getReplacements());

		IconRenderer.getInstance().dispose();

		root.put("sources", sources);
		
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(Mod.class, new ModSerializer())
				.registerTypeAdapter(Machine.class, new MachineSerializer())
				.registerTypeAdapter(ItemList.class, new ItemListSerializer())
				.serializeNulls()
				.create();
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
		List<Machine> machines = new ArrayList<Machine>();
		for(GT_Recipe_Map map : GT_Recipe_Map.sMappings){
			Machine mach = new Machine(GT_LanguageManager.getTranslation(map.mUnlocalizedName));
			
			// machine name retrieval
			if(mach.name == null || mach.name.isEmpty()){
				mach.name = map.mUnlocalizedName;
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
				
				mach.recipes.add(gtr);
			}
			machines.add(mach);
		}
		
		return new Mod("gregtech", machines);
	}

	private Machine getOreDictShapedRecipes(){
		List<Recipe> retRecipes = new ArrayList<>();
		List<?> recipes = CraftingManager.getInstance().getRecipeList();
		for(Object obj : recipes){
			if(obj instanceof ShapedOreRecipe){
				ShapedOreRecipe original = (ShapedOreRecipe) obj;
				OreDictShapedRecipe rec = new OreDictShapedRecipe();

				for(Object stack : original.getInput()){
					if (stack instanceof ItemStack) {
						Item item = RecipeUtil.formatRegularItemStack((ItemStack)stack);
						rec.iI.add(item);
					}else if (stack instanceof net.minecraft.item.Item){
						rec.iI.add(RecipeUtil.formatRegularItemStack(new ItemStack((net.minecraft.item.Item)stack)));
					}else if (stack instanceof Block){
						rec.iI.add(RecipeUtil.formatRegularItemStack(new ItemStack((Block)stack,1,Short.MAX_VALUE)));
					}else if (stack instanceof ArrayList && !((ArrayList)stack).isEmpty()) {
						@SuppressWarnings("unchecked")
						ItemStack item = ((ArrayList<ItemStack>)stack).get(0);
						rec.iI.add(getOreDictNames(item));
					}
				}

				rec.o = RecipeUtil.formatRegularItemStack(original.getRecipeOutput());

				retRecipes.add(rec);
			}
		}

		return new Machine("shapedOre", retRecipes);
	}

	private Machine getOreDictShapelessRecipes(){
		List<Recipe> retRecipes = new ArrayList<>();
		List<?> recipes = CraftingManager.getInstance().getRecipeList();
		for(Object obj : recipes){
			if(obj instanceof ShapelessOreRecipe){
				ShapelessOreRecipe original = (ShapelessOreRecipe) obj;
				OreDictShapelessRecipe rec = new OreDictShapelessRecipe();

				for(Object stack : original.getInput()){
					if (stack instanceof ItemStack) {
						Item item = RecipeUtil.formatRegularItemStack((ItemStack)stack);
						rec.iI.add(item);
					}else if (stack instanceof net.minecraft.item.Item){
						rec.iI.add(RecipeUtil.formatRegularItemStack(new ItemStack((net.minecraft.item.Item)stack)));
					}else if (stack instanceof Block){
						rec.iI.add(RecipeUtil.formatRegularItemStack(new ItemStack((Block)stack, 1, Short.MAX_VALUE)));
					}else if (stack instanceof ArrayList && !((ArrayList)stack).isEmpty()){
						@SuppressWarnings("unchecked")
						ItemStack item = ((ArrayList<ItemStack>)stack).get(0);
						rec.iI.add(getOreDictNames(item));
					}
				}

				rec.o = RecipeUtil.formatRegularItemStack(original.getRecipeOutput());

				retRecipes.add(rec);
			}
		}

		return new Machine("shapelessOre", retRecipes);
	}

	private List<String> getOreDictNames(ItemStack itemStack){
		int[] ids = OreDictionary.getOreIDs(itemStack);
		ArrayList<String> names = new ArrayList<>();
		for(int id : ids){
			names.add(OreDictionary.getOreName(id));
		}
		return names;
	}

	private ItemList getReplacements(){
		List<ItemBase> oreDictItems = new ArrayList<>();
		String[] oreNames = OreDictionary.getOreNames();
		for(String name : oreNames){
			oreDictItems.add(new OreDictItem(name, getReplacements(name)));
		}

		return new ItemList("replacements", oreDictItems);
	}

	private List<Item> getReplacements(String name){
		List<ItemStack> recipeItemList = OreDictionary.getOres(name);
		Set<Item> replacements = new HashSet<>();
		for (ItemStack inList : recipeItemList){
			Item item = RecipeUtil.formatRegularItemStack(inList);
			replacements.add(item);
		}
		return new ArrayList<>(replacements);
	}

	private Machine getShapedRecipes(){
		List<Recipe> retRecipes = new ArrayList<>();
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
		
		return new Machine("shaped", retRecipes);
	}

	private Machine getShapelessRecipes(){
		List<Recipe> retRecipes = new ArrayList<>();
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
		
		return new Machine("shapeless", retRecipes);
	}
	
	private void saveData(String json){
		try {
			FileWriter writer = new FileWriter(getSaveFile());
			writer.write(json);
			writer.close();
			
			RecipeExporterMod.log.info("Recipes have been exported.");
		} catch (IOException | NullPointerException e) {
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
