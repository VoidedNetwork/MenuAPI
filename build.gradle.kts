import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("maven-publish")
    id("io.freefair.lombok") version "8.10"
    id("com.gradleup.shadow") version "8.3.0"
}

object Project {
    const val NAME = "MenuAPI"
    const val GROUP = "gg.voided"
    const val AUTHOR = "J4C0B3Y"
    const val VERSION = "1.3"
}

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/nms/")
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")
}

java {
    withSourcesJar()
}

tasks {
    register<Copy>("copy") {
        from(named("shadowJar"))
        rename("(.*)-all.jar", "${Project.NAME}-${Project.VERSION}.jar")
        into(file("jars"))
    }

    register("delete") {
        file("jars").deleteRecursively()
    }

    register("install") {
        dependsOn(named("publishReleasePublicationToMavenLocal"))
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = Project.NAME
            groupId = Project.GROUP
            version = Project.VERSION

            artifact(tasks.named<ShadowJar>("shadowJar").get().archiveFile)

            artifact(tasks.named<Jar>("sourcesJar").get().archiveFile) {
                classifier = "sources"
            }
        }
    }
}
