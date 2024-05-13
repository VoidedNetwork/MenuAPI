# MenuAPI

Simple to use, high performance bukkit menu api.

## Features

- Inbuilt paginated menus
- Asynchronous menu option
- Foreground & background layers
- Reusable layer templates
- Auto update annotation
- Small and lightweight (~32kb)

## Installation

Prebuilt jars can be found in [releases](https://github.com/VoidedNetwork/MenuAPI/releases).

> **NOTE:** <br/>
> Please relocate the library as multiple menu handlers can
> cause problems with other plugins that also use the MenuAPI.

### Building

1. Clone this repository and enter its directory.
2. Run the intellij build configuration by clicking the top right icon.
3. Alternatively you can run `gradle classes shadowJar delete copy`.
4. The output jar file will be located in the `jars` directory.

## Usage

### Buttons