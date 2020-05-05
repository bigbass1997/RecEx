package com.bigbass.recex.recipes.serializers;

import com.bigbass.recex.recipes.ItemList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ItemListSerializer implements JsonSerializer<ItemList> {
    @Override
    public JsonElement serialize(ItemList src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("type", context.serialize(src.type));
        object.add("items", context.serialize(src.itemList));
        return object;
    }
}
