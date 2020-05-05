package com.bigbass.recex.recipes.serializers;

import com.bigbass.recex.recipes.Mod;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ModSerializer implements JsonSerializer<Mod> {
    @Override
    public JsonElement serialize(Mod src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("type", context.serialize(src.modName));
        object.add("machines", context.serialize(src.machines));
        return object;
    }
}
