# Shield API

Shield API allows mod authors to easily add shields with custom models.

## Installation

Add this mod as dependency for your project.

build.gradle

```groovy
repositories {
    maven {
        name = 'Modrinth'
        url = 'https://api.modrinth.com/maven'
        content {
            includeGroup 'maven.modrinth'
        }
    }
}

dependencies {
    modImplementation "maven.modrinth:shield-api:${project.shield_api_version}"
}
```

gradle.properties

```
# replace with latest version
shield_api_version=2.3.0
```

## Usage

- Create your item instance by calling the constructor for the CustomShieldItem
  (it applies the vanilla `minecraft:blocks_attacks`, `minecraft:equippable` and attribute components for you).
- Register your item instance.
- Add model and texture files (e.g. using BlockBench)
- Add an item model definition `assets/<namespace>/items/<shield_id>.json` that switches to the blocking model
  (item model `overrides` were removed in 1.21.4):

```json
{
  "model": {
    "type": "minecraft:condition",
    "property": "minecraft:using_item",
    "on_false": { "type": "minecraft:model", "model": "<namespace>:item/<shield_id>" },
    "on_true":  { "type": "minecraft:model", "model": "<namespace>:item/<shield_id>_blocking" }
  }
}
```

- Add your item to the fabric convention tag for shields "c:shields" for better compatibility with other mods

A simple example can be found on [GitHub](https://github.com/FabricExtras/ShieldAPI/blob/main/fabric/src/testmod_fabric/java/net/fabric_extras/shield_api_test/ShieldAPITest.java).

## Tips for creating a custom model
Note that the elements, groups and textures of the example shields are defined in a separate file called "<item_name>_template", which is then inherited by the actual item model files. This is not required, but reduces redundant data. The display settings are the same for both items. Feel free to use the same settings for your own items.