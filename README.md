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
- Add model and texture files (e.g. using BlockBench).

A simple example can be found
on [GitHub](https://github.com/TheRedBrain/shield-api/blob/main/src/testmod/java/com/github/theredbrain/shieldapitest/ShieldAPITest.java).