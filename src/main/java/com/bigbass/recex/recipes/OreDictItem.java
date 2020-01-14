package com.bigbass.recex.recipes;

import com.google.common.collect.Lists;

import java.util.List;

public class OreDictItem {
    public String name;
    public List<Item> reps;

    public OreDictItem(String oreDictName, List<Item> replacements){
        this.name = oreDictName;
        this.reps = Lists.newArrayList(replacements);
    }
}
