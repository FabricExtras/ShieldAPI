# 3.0.0

1.21.2 and 1.21.4 made massive changes to items, including shields. This means that Shield API is nearly obsolete, how exciting!

But we are not there quite yet, as Minecraft still checks specifically for the "minecraft:shield" item in some places.

Custom shields are now created using 100% vanilla tech, see the test mod for an example.

Note: Shields now directly use the 'attribute modifier component'. This means that they can be changed for individual item stacks. Changing the default is more involved, however. There are already other mods out there doing exactly that, which is why I choose to remove the feature from Shield API.

# 2.0.2

- lowered Fabric Loader requirements

# 2.0.1

- now compatible with 1.21

# 2.0.0

- updated to 1.21.1

# 1.0.1

- fixed an issue were custom shields would not take damage when blocking an attack

# 1.0.0

Initial release.

#