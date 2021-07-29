[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
### <div align="center">This mod is no longer maintained</div>
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
| Oredict Shaped | Fully Supported |
| Oredict Shapeless | None |
| Gregtech 5 | Fully Supported (including all mods using the Gregtech API) |
| Bartworks | Fully Supported
| GT++ | All except possibly 1-4 recipes
| Forestry | None
| Thaumcraft 4 | None

### Building
Building the mod can be done using the gradle wrapper provided in the repo, with the command `gradlew build`.

### License
This mod/sourcecode is licensed under the GNU General Public License v3, except where other licenses are included (Forge, FML, and Minecraft for example).

### Contributions
~~If you are interested in contributing, feel free to submit issues or pull requests as you see fit. I'm sure there are things that could be programmed better or perhaps more efficiently, but since this is being developed alongside NotEnoughProduction and may change at any moment, I am not too worried about how good/bad the code is for the time being.~~
