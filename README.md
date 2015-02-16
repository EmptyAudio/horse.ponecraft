# horse.ponecraft
Source for the custom mods on the ponecraft.horse server
* [What is this?](#packages)
* [What's done?](https://github.com/EmptyAudio/horse.ponecraft/issues)
* [How do I help?](#contributing)

## Packages
### horse.ponecraft.earth
This package contains the Cooktop block and any tweaks required for earth ponies that can't be done via permissions or configuration.

### horse.ponecraft.pegasus
Contains the code that allows pegasi to fly as long as they have sufficient hunger.

### horse.ponecraft.food
Shared package that implements the nutrition system.

## Contributing
If you want to help or just try things out on your own, here's how to get a development environment set up. You'll need the following:
* [The latest JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Eclipse](https://www.eclipse.org/downloads/)
* [The src distribution of Forge 1.7.10-10.13.2.1291.](http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.7.10-10.13.2.1291/forge-1.7.10-10.13.2.1291-src.zip)
* Some prerequisite mods:
  * AppleCore-mc1.7.10-1.1.0+89.1415c.jar
  * CodeChickenCore-1.7.10-1.0.4.29-universal.jar
  * NotEnoughItems-1.7.10-1.0.3.72-universal.jar
  * Pam's HarvestCraft 1.7.10f.jar

With all those downloaded, you're ready to set things up:
* Extract the Forge src zip somewhere
* Clone this repo into that folder (gradlew.bat and src from this repo should be in the same folder)
* Open a command window in that folder and run:
  * ```gradlew setupDecompWorkspace --refresh-dependencies```
  * ```gradlew eclipse```
* Put all the prerequisite mods into ```eclipse/mods```
* Open up Eclipse and set ```eclipse``` as your workspace
