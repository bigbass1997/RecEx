package com.bigbass.recex.recipes.serializers;

import com.bigbass.recex.recipes.Machine;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class MachineSerializer implements JsonSerializer<Machine> {
    @Override
    public JsonElement serialize(Machine src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("n", context.serialize(src.name));
        object.add("recs", context.serialize(src.recipes));
        return object;
    }
}
