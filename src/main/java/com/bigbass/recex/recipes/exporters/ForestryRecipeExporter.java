package com.bigbass.recex.recipes.exporters;

import com.bigbass.recex.recipes.*;
import com.bigbass.recex.recipes.forestry.RfRecipe;
import com.bigbass.recex.recipes.gregtech.RecipeUtil;
import forestry.api.fuels.FermenterFuel;
import forestry.api.fuels.FuelManager;
import forestry.api.recipes.*;
import forestry.energy.EnergyManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForestryRecipeExporter implements RecipeExporter {

    @Override
    public Mod getRecipes() {
        List<Machine> machines = new ArrayList<>();
        machines.add(getCarpenterRecipes());
        machines.add(getCentrifugeRecipes());
        machines.add(getFermenterRecipes());
        machines.add(getMoistenerRecipes());
        machines.add(getSqueezerRecipes());
        machines.add(getStillRecipes());
        machines.add(getFabricatorRecipes());
        return new Mod("forestry", machines);
    }

    private Machine getCarpenterRecipes() {
        Machine machine = new Machine(StatCollector.translateToLocal("tile.for.factory.1.name"));

        for (ICarpenterRecipe recipe : RecipeManagers.carpenterManager.recipes()) {
            RfRecipe rfRecipe = new RfRecipe();
            rfRecipe.en = true;
            rfRecipe.dur = recipe.getPackagingTime();
            rfRecipe.rft = EnergyManager.scaleForDifficulty(2040 / 10) / 4;

            for (Object ingredient : recipe.getCraftingGridRecipe().getIngredients()) {
                if (ingredient instanceof ItemStack) {
                    rfRecipe.iI.add(RecipeUtil.formatRegularItemStack((ItemStack) ingredient));
                } else if (ingredient instanceof ItemStack[]) {
                    for (ItemStack stack : (ItemStack[]) ingredient) {
                        rfRecipe.iI.add(RecipeUtil.formatRegularItemStack(stack));
                    }
                } else if (ingredient instanceof List) {
                    for (ItemStack stack : (List<ItemStack>) ingredient) {
                        rfRecipe.iI.add(RecipeUtil.formatRegularItemStack(stack));
                    }
                }
            }

            if (recipe.getBox() != null) {
                rfRecipe.iI.add(RecipeUtil.formatRegularItemStack(recipe.getBox()));
            }

            if (recipe.getFluidResource() != null) {
                rfRecipe.fI.add(RecipeUtil.formatRegularFluidStack(recipe.getFluidResource()));
            }

            ItemStack output = recipe.getCraftingGridRecipe().getRecipeOutput();
            if (output != null) {
                rfRecipe.iO.add(RecipeUtil.formatRegularItemStack(output));
            }

            machine.recipes.add(rfRecipe);
        }

        return machine;
    }

    private Machine getCentrifugeRecipes() {
        Machine machine = new Machine(StatCollector.translateToLocal("tile.for.factory.2.name"));

        for (ICentrifugeRecipe recipe : RecipeManagers.centrifugeManager.recipes()) {
            RfRecipe rfRecipe = new RfRecipe();
            rfRecipe.en = true;
            rfRecipe.dur = recipe.getProcessingTime();
            rfRecipe.rft = EnergyManager.scaleForDifficulty(3200 / 20) / 4;

            ItemStack input = recipe.getInput();
            if (input != null) {
                rfRecipe.iI.add(RecipeUtil.formatRegularItemStack(input));
            }

            for (Map.Entry<ItemStack, Float> output : recipe.getAllProducts().entrySet()) {
                if (output != null && output.getKey() != null) {
                    Item item = RecipeUtil.formatRegularItemStack(output.getKey());
                    ItemRandom itemRandom = new ItemRandom(item, output.getValue());
                    rfRecipe.iO.add(itemRandom);
                }
            }

            machine.recipes.add(rfRecipe);
        }

        return machine;
    }

    private Machine getFermenterRecipes() {
        Machine machine = new Machine(StatCollector.translateToLocal("tile.for.factory.3.name"));

        for (Map.Entry<ItemStack, FermenterFuel> entry : FuelManager.fermenterFuel.entrySet()) {
            int fermentPerCycle = entry.getValue().fermentPerCycle;

            Item fuelItem = RecipeUtil.formatGregtechItemStack(entry.getKey());
            ItemMetaData fuel = null;
            if (fuelItem != null) {
                fuel = new ItemMetaData(fuelItem, "catalyst");
            }

            for (IFermenterRecipe recipe : RecipeManagers.fermenterManager.recipes()) {
                int extractionDuration = (int) Math.ceil((double) recipe.getFermentationValue() / fermentPerCycle);

                RfRecipe rfRecipe = new RfRecipe();
                rfRecipe.en = true;
                rfRecipe.dur = 20 + (extractionDuration * 5);
                rfRecipe.rft = EnergyManager.scaleForDifficulty(4200) / 4;

                if (fuel != null) {
                    rfRecipe.iI.add(fuel);
                }

                ItemStack input = recipe.getResource();
                if (input != null) {
                    rfRecipe.iI.add(RecipeUtil.formatRegularItemStack(input));
                }

                FluidStack fluidInput = recipe.getFluidResource();
                if (fluidInput != null) {
                    fluidInput.amount = fermentPerCycle * extractionDuration;
                    rfRecipe.fI.add(RecipeUtil.formatRegularFluidStack(fluidInput));
                }

                if (recipe.getOutput() != null) {
                    int amount = Math.round(recipe.getFermentationValue() * recipe.getModifier());
                    Fluid output = RecipeUtil.formatRegularFluidStack(new FluidStack(recipe.getOutput(), amount));
                    rfRecipe.fO.add(output);
                }

                machine.recipes.add(rfRecipe);
            }
        }

        return machine;
    }

    private Machine getMoistenerRecipes() {
        Machine machine = new Machine(StatCollector.translateToLocal("tile.for.factory.4.name"));

        for (IMoistenerRecipe recipe : RecipeManagers.moistenerManager.recipes()) {
            RfRecipe rfRecipe = new RfRecipe();
            rfRecipe.en = true;
            rfRecipe.dur = recipe.getTimePerItem() / 4; // assume full efficiency
            rfRecipe.rft = 0;

            ItemStack input = recipe.getResource();
            if (input != null) {
                rfRecipe.iI.add(RecipeUtil.formatRegularItemStack(input));
            }

            rfRecipe.fI.add(RecipeUtil.formatRegularFluidStack(new FluidStack(FluidRegistry.WATER, rfRecipe.dur)));

            ItemStack output = recipe.getProduct();
            if (output != null) {
                rfRecipe.iO.add(RecipeUtil.formatRegularItemStack(output));
            }

            machine.recipes.add(rfRecipe);
        }

        return machine;
    }

    private Machine getSqueezerRecipes() {
        Machine machine = new Machine(StatCollector.translateToLocal("tile.for.factory.5.name"));

        for (ISqueezerRecipe recipe : RecipeManagers.squeezerManager.recipes()) {
            RfRecipe rfRecipe = new RfRecipe();
            rfRecipe.en = true;
            rfRecipe.dur = recipe.getProcessingTime();
            rfRecipe.rft = EnergyManager.scaleForDifficulty(1100 / 10) / 4;

            for (ItemStack input : recipe.getResources()) {
                if (input != null) {
                    rfRecipe.iI.add(RecipeUtil.formatRegularItemStack(input));
                }
            }

            if (recipe.getRemnants() != null) {
                Item item = RecipeUtil.formatRegularItemStack(recipe.getRemnants());
                rfRecipe.iO.add(new ItemRandom(item, recipe.getRemnantsChance()));
            }

            if (recipe.getFluidOutput() != null) {
                rfRecipe.fO.add(RecipeUtil.formatRegularFluidStack(recipe.getFluidOutput()));
            }

            machine.recipes.add(rfRecipe);
        }

        return machine;
    }

    private Machine getStillRecipes() {
        Machine machine = new Machine(StatCollector.translateToLocal("tile.for.factory.6.name"));

        for (IStillRecipe recipe : RecipeManagers.stillManager.recipes()) {
            RfRecipe rfRecipe = new RfRecipe();
            rfRecipe.en = true;
            rfRecipe.dur = recipe.getCyclesPerUnit();
            rfRecipe.rft = EnergyManager.scaleForDifficulty(200) / 4;

            if (recipe.getInput() != null) {
                rfRecipe.fI.add(RecipeUtil.formatRegularFluidStack(recipe.getInput()));
            }

            if (recipe.getOutput() != null) {
                rfRecipe.fO.add(RecipeUtil.formatRegularFluidStack(recipe.getOutput()));
            }

            machine.recipes.add(rfRecipe);
        }

        return machine;
    }

    private Machine getFabricatorRecipes() {
        Machine machine = new Machine(StatCollector.translateToLocal("tile.for.factory2.0.name"));

        for (IFabricatorRecipe recipe : RecipeManagers.fabricatorManager.recipes()) {
            RfRecipe rfRecipe = new RfRecipe();
            rfRecipe.en = true;
            rfRecipe.dur = 5;
            rfRecipe.rft = EnergyManager.scaleForDifficulty(200) / 4;

            for (Object ingredient : recipe.getIngredients()) {
                if (ingredient instanceof ItemStack) {
                    rfRecipe.iI.add(RecipeUtil.formatRegularItemStack((ItemStack) ingredient));
                } else if (ingredient instanceof ItemStack[]) {
                    for (ItemStack stack : (ItemStack[]) ingredient) {
                        rfRecipe.iI.add(RecipeUtil.formatRegularItemStack(stack));
                    }
                } else if (ingredient instanceof List) {
                    for (ItemStack stack : (List<ItemStack>) ingredient) {
                        rfRecipe.iI.add(RecipeUtil.formatRegularItemStack(stack));
                    }
                }
            }

            if (recipe.getLiquid() != null) {
                rfRecipe.fI.add(RecipeUtil.formatRegularFluidStack(recipe.getLiquid()));
            }

            if (recipe.getRecipeOutput() != null) {
                rfRecipe.iO.add(RecipeUtil.formatRegularItemStack(recipe.getRecipeOutput()));
            }

            machine.recipes.add(rfRecipe);
        }

        return machine;
    }
}
