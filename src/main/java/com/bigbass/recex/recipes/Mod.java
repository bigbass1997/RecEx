package com.bigbass.recex.recipes;

import java.util.List;

public class Mod {
    public String modName;
    public List<Machine> machines;

    public Mod(String modName, List<Machine> machines) {
        this.modName = modName;
        this.machines = machines;
    }
}
