[![License: CC BY-NC-SA 4.0](https://licensebuttons.net/l/by-nc-sa/4.0/80x15.png)](https://creativecommons.org/licenses/by-nc-sa/4.0/)
### Description
RexEx is a recipe exporter mod for Minecraft. The mod is/will-be capable of exporting recipes for a wide range of other mods, not just vanilla recipes, including Gregtech 5.

Since the export process happens while in a loaded world/server, any recipe modifications/additions that happen during init/postInit, including any changes made by MineTweaker, are automatically included in recipes exported from this mod. Recipes are exported into a minimized JSON format, such that keys are limited to only a few characters, and all whitespace/newlines are removed. The mod will be capable of exporting a compressed version instead, which will greatly reduce file size.

### Usage
The default keybind to open the exporter's GUI is 'k', however you should be able to remap this in the controls menu of Minecraft. At this time, the GUI is rather simple, including just a single button that will execute the export process. Hit ESC to close the GUI. The exported files will be stored in `RecEx-Records/`, at the root of the minecraft instance.

### Recipe Source Compatibility
| Source | Status |
| ------ | ------ |
| Shaped | Fully Supported |
| Shapeless | Fully Supported |
| Oredict Shaped | None |
| Oredict Shapeless | None |
| Gregtech 5 | Fully Supported (including all mods using the Gregtech API) |
| Bartworks | Fully Supported
| GT++ | All except possibly 1-4 recipes
| Forestry | None
| Thaumcraft 4 | None

### Building
Building the mod can be done using the gradle wrapper provided in the repo, with the command `gradlew build`.

### License
This mod is licensed under Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International, except where other licenses are included (Forge, FML, and Minecraft for example).

A full copy of the project license can be viewed in "LICENSE.txt", and a summary plus additional links are provided in "LICENSE.md".

### Contributions
Posting issues, feature ideas, and pull requests are highly encouraged! However, please note, due to the early age of this mod, PR's may be left open until an appropriate time is reached for the changes to be implemented. If this is the case for a particular PR, a maintainer will reply to the PR to make note of this.