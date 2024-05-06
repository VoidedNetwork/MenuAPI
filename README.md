# MenuAPI

Simple to use, high performance bukkit menu API.

## Features

- Quick and simple usage
- Async buttons / menu updating
- Paginated menus
- Auto update annotation
- Build for performance

## Usage

Please see this repo's [wiki](https://github.com/VoidedNetwork/MenuAPI/wiki) for API usage and examples.

## Installing

<details>
<summary>Gradle</summary>

#### Repository

```kotlin
maven { url = uri("https://jitpack.io") }
```

#### Dependency

Make sure to replace `VERSION` with the newest release.

```kotlin
implementation("com.github.VoidedNetwork:MenuAPI:VERSION")
```
</details>

<details>
<summary>Maven</summary>

#### Repository

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```

#### Dependency

Make sure to replace `VERSION` with the newest release.

```xml
<dependency>
    <groupId>com.github.User</groupId>
    <artifactId>Repo</artifactId>
    <version>Tag</version>
</dependency>
```
</details>

<details>
<summary>Building</summary>

1. Clone this repository and enter its directory.
2. Run the intellij build configuration by clicking the top right icon.
3. Alternatively you can run `gradle classes shadowJar delete copy`.
4. The output jar file will be located in the `jars` directory.
</details>

> **NOTE:** <br/>
> Please relocate the library as multiple menu handlers can
> cause problems with other plugins that also use the MenuAPI.