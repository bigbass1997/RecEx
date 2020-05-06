package com.bigbass.recex.recipes.forestry;

import com.bigbass.recex.recipes.Fluid;
import com.bigbass.recex.recipes.Item;
import com.bigbass.recex.recipes.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RfRecipe implements Recipe {
    public boolean en;
    public int dur;
    public int rft;
    public List<Item> iI;
    public List<Item> iO;
    public List<Fluid> fI;
    public List<Fluid> fO;

    public RfRecipe() {
        iI = new ArrayList<>();
        iO = new ArrayList<>();
        fI = new ArrayList<>();
        fO = new ArrayList<>();
    }
}
