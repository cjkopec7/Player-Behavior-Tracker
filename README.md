# PLAYER BEHAVIOR TRACKER MOD v1.1

by Colby Kopec

## Installation

1. Install [Minecraft Java Edition](https://www.minecraft.net/en-us/download) (version 1.16.4)
2. Download [Minecraft Forge](http://files.minecraftforge.net/) recommeneded MDK (version 1.16.4 - 35.1.0)
3. Use IDE such as [Eclipse IDE for Java](https://www.eclipse.org/downloads/packages/release/kepler/sr1/eclipse-ide-java-developers)
4. Import this as a Gradle project into your workspace, and run Gradle task 'genEclipseRuns'
5. Change your run arguments for task 'runClient' to your Mojang account login information

```bash
--username "you@email.com" --password "yourpass"
```

6. Create a 'Test' world and change the directory within the TestListener class to the location of your world's stat folder.

```java
public class TestListener {
String dir = "C:\\[project_folder]\\run\\saves\\[world_name]\\stats";
you = new Scoreboard(new Player(player.getUniqueID(), playerName, dir)); }
```

7. Launch 'runClient' to begin Minecraft

## Credits
A University of North Carolina Wilmington CSC-450 Software Engineering Project

Colby Kopec, Eric Dorshimer, Justin Balderson, Max Groover, Noah Gilgo, Taylor Dalrymple, Carl Blackmon (Tester)

Special thanks to Dr. Vetter and Dr. Cariveau
