package com.bigbass.recex.recipes;

public class ItemMetaData extends Item {
    public String meta;

    public ItemMetaData(Item item, String metaData) {
        super(item.a, item.uN, item.lN);
        this.meta = metaData;
    }
}
