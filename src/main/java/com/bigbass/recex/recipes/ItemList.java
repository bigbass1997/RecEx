package com.bigbass.recex.recipes;

import java.util.List;

public class ItemList {
    public String type;
    public List<ItemBase> itemList;

    public ItemList(String type, List<ItemBase> itemList) {
        this.type = type;
        this.itemList = itemList;
    }
}
