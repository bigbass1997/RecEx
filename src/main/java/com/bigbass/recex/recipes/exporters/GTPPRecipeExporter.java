package com.bigbass.recex.recipes.exporters;

import com.bigbass.recex.recipes.Fluid;
import com.bigbass.recex.recipes.Item;
import com.bigbass.recex.recipes.Machine;
import com.bigbass.recex.recipes.Mod;
import com.bigbass.recex.recipes.gregtech.GregtechRecipe;
import com.bigbass.recex.recipes.gregtech.RecipeUtil;
import gregtech.api.util.GTPP_Recipe;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Recipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GTPPRecipeExporter implements RecipeExporter {

    @Override
    public Mod getRecipes() {
        List<Machine> machines = new ArrayList<>();
        for (GTPP_Recipe.GTPP_Recipe_Map map : GTPP_Recipe.GTPP_Recipe_Map.sMappings) {
            machines.add(getMachine(map.mUnlocalizedName, map.mRecipeList));
        }
        for (GTPP_Recipe.GTPP_Recipe_Map_Internal map : GTPP_Recipe.GTPP_Recipe_Map_Internal.sMappingsEx) {
            machines.add(getMachine(map.mUnlocalizedName, map.mRecipeList));
        }
        return new Mod("gregtech", machines);
    }

    private <T extends GT_Recipe> Machine getMachine(String unlocalizedName, Collection<T> recipeList) {
        Machine machine = new Machine(GT_LanguageManager.getTranslation(unlocalizedName));

        // machine name retrieval
        if (machine.name == null || machine.name.isEmpty()) {
            machine.name = unlocalizedName;
        }

        for (GT_Recipe rec : recipeList) {
            GregtechRecipe gtr = new GregtechRecipe();
            gtr.en = rec.mEnabled;
            gtr.dur = rec.mDuration;
            gtr.eut = rec.mEUt;

            // item inputs
            for (ItemStack stack : rec.mInputs) {
                Item item = RecipeUtil.formatGregtechItemStack(stack);
                if (item == null) {
                    continue;
                }
                gtr.iI.add(item);
            }

            // item outputs
            for (ItemStack stack : rec.mOutputs) {
                Item item = RecipeUtil.formatGregtechItemStack(stack);
                if (item == null) {
                    continue;
                }
                gtr.iO.add(item);
            }

            // fluid inputs
            for (FluidStack stack : rec.mFluidInputs) {
                Fluid fluid = RecipeUtil.formatGregtechFluidStack(stack);
                if (fluid == null) {
                    continue;
                }
                gtr.fI.add(fluid);
            }

            // fluid outputs
            for (FluidStack stack : rec.mFluidOutputs) {
                Fluid fluid = RecipeUtil.formatGregtechFluidStack(stack);
                if (fluid == null) {
                    continue;
                }
                gtr.fO.add(fluid);
            }
            machine.recipes.add(gtr);
        }
        return machine;
    }
}
