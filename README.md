# horse.ponecraft
Source for the custom mods on the ponecraft.horse server
* [What the heck is a ponecraft?](http://mlpg.co/v/res/2540.html)
* [What functionality is in this?](#packages)
* [How do I help?](#contributing)

## Packages
### horse.ponecraft.earth
This package contains the Cooktop block and any tweaks required for earth ponies that can't be done via permissions or configuration.

### horse.ponecraft.food
Shared package that implements the nutrition system.

### horse.ponecraft.truffles
Since ponies don't eat meat and that's all pigs drop, this package adds truffles. Pigs generate truffles much the same way chickens lay eggs. Truffles can be brewed into a Resistance potion in a brewing stand, dropped into a mana pool to create Magic Truffles, tossed into a crucible to alchemize Vis Truffles, and gilded in a casting table to make Gold Truffles.

### horse.ponecraft.pegasus
Contains the code that allows pegasi to fly as long as they have sufficient hunger. Integrates with ForgeEssentials to add a new permission for pegasus flight.

### horse.ponecraft.unicorn
Removes the Thaumostatic Harness from the Thaumonomicon as only pegasi are allowed to fly.

## Other-Stuff

## Contributing
If you want to help or just try things out on your own, here's how to get a development environment set up. You'll need the following:
* [The latest JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Eclipse](https://www.eclipse.org/downloads/)
* [The src distribution of Forge 1.7.10-10.13.2.1291.](http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.7.10-10.13.2.1291/forge-1.7.10-10.13.2.1291-src.zip)
* Some prerequisite mods:
  * AppleCore-mc1.7.10-1.1.0+89.1415c.jar
  * CodeChickenCore-1.7.10-1.0.4.29-universal.jar
  * NotEnoughItems-1.7.10-1.0.3.72-universal.jar
  * ProjectRed-1.7.10-4.5.8.59-Base.jar
  * Thaumcraft-1.7.10-4.2.3.4.jar
  * Pam's HarvestCraft 1.7.10f.jar

With all those downloaded, you're ready to set things up:
* Extract the Forge src zip somewhere
* Clone this repo into that folder (gradlew.bat and src from this repo should be in the same folder)
* Open a command window in that folder and run:
  * ```gradlew setupDecompWorkspace --refresh-dependencies```
  * ```gradlew eclipse```
* Put all the prerequisite mods into ```eclipse/mods```
* Open up Eclipse and set ```eclipse``` as your workspace

To actually get a test world to work:
* Run the game
* Close Minecraft
* Edit eclipse/ForgeEssentials/main.cfg
  * Change line 50 to ```B:WorldBorder=false```. There's a bug in the ForgeEssentials WorldBorder module that causes a crash soon after joining a server.

After that everything should work fine. If it doesn't, open up an issue and include any crash logs.