package com.bigbass.recex.recipes;

import java.util.ArrayList;
import java.util.List;

public class Machine {
    public String name;
    public List<Recipe> recipes;

    public Machine(String name, List<Recipe> recipes) {
        this.name = name;
        this.recipes = recipes;
    }

    public Machine(String name) {
        this(name, new ArrayList<>());
    }
}
