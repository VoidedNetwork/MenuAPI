# MenuAPI

Simple to use, high performance bukkit menu api.

## Features

- Inbuilt paginated menus
- Asynchronous menu option
- Foreground & background layers
- Reusable layer templates
- Auto update annotation
- Small and lightweight (~32kb)

## Support

If you need any assistance using or installing my MenuAPI,
feel free to contact me by either adding me on discord (@J4C0B3Y)
or by creating an issue and explaining your problem or question.

## Usage

Documentation and examples can be found in this repo's [wiki](https://github.com/VoidedNetwork/MenuAPI/wiki).

## Installation

Prebuilt jars can be found in [releases](https://github.com/VoidedNetwork/MenuAPI/releases).

> **NOTE:** <br/>
> Please relocate the library as multiple menu handlers can
> cause problems with other plugins that also use the MenuAPI.

### Maven & Gradle

Replace `VERSION` with the latest release version on GitHub.

```kts
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.VoidedNetwork:MenuAPI:VERSION")
}
```

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.VoidedNetwork</groupId>
        <artifactId>MenuAPI</artifactId>
        <version>VERSION</version>
    </dependency>
</dependencies>
```

### Building

1. Clone this repository and enter its directory.
2. Run the intellij build configuration by clicking the top right icon.
3. Alternatively you can run `gradle classes shadowJar delete copy`.
4. The output jar file will be located in the `jars` directory.