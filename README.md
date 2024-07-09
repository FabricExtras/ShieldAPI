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
shield_api_version=1.0.0
```

## Usage

- Create your item instance by calling the constructor for the CustomShieldItem.
- Register your item instance.
- Add model and texture files (e.g. using BlockBench)
- Add your item to the fabric convention tag for shields "c:shields" for better compatibility with other mods

A simple example can be found on [GitHub](https://github.com/FabricExtras/ShieldAPI/blob/main/src/testmod/java/net/fabric_extras/shield_api_test/ShieldAPITest.java).

## Tips for creating a custom model
Note that the elements, groups and textures of the example shields are defined in a separate file called "<item_name>_template", which is then inherited by the actual item model files. This is not required, but reduces redundant data. The display settings are the same for both items. Feel free to use the same settings for your own items.