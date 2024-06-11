plugins {
    id("java")
    id("maven-publish")
    id("io.freefair.lombok") version "8.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

object Project {
    const val NAME = "MenuAPI"
    const val GROUP = "gg.voided"
    const val VERSION = "1.2"
}

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/nms/")
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")
}

tasks {
    register<Copy>("copy") {
        from(named("shadowJar"))
        rename("(.*)-all.jar", "${Project.NAME}-${Project.VERSION}.jar")
        into(file("jars"))
    }

    register("delete") {
        doLast { file("jars").deleteRecursively() }
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = Project.NAME
            groupId = Project.GROUP
            version = Project.VERSION

            from(components["java"])
        }
    }
}