plugins {
    id("java")
    id("maven-publish")
    id("io.freefair.lombok") version "8.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

var library = "MenuAPI"
version = "1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.codemc.io/repository/nms/") }
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")
}

tasks {
    register<Copy>("copy") {
        from(named("shadowJar"))
        rename("(.*)-all.jar", "$library-$version.jar")
        into(file("jars"))
    }

    register("delete") {
        doLast { file("jars").deleteRecursively() }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "gg.voided"
            artifactId = "MenuAPI"
            version = version

            from(components["java"])
        }
    }
}