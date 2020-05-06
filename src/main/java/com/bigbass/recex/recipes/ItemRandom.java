package com.bigbass.recex.recipes;

public class ItemRandom extends Item {
    public float percentage;

    public ItemRandom(Item item, float percentage) {
        super(item.a, item.uN, item.lN);
        this.percentage = percentage;
    }
}
