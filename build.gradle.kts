plugins {
    kotlin("jvm") version "2.0.21"
    id("io.papermc.paperweight.userdev") version "1.7.5"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.gradleup.shadow") version "8.3.5"
}

val versionString = "1.7.7"

group = "de.bypixeltv"
version = versionString

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.skriptlang.org/releases")
    }
}

dependencies {
    paperweight.paperDevBundle("1.21.3-R0.1-SNAPSHOT")

    bukkitLibrary(libs.kspigot) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    bukkitLibrary(libs.commandapi.kotlin) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    bukkitLibrary(libs.commandapi.shade) {
        exclude(group = "org.bukkit", module = "bukkit")
    }

    compileOnly(libs.skript) {
        exclude(group = "org.bukkit", module = "bukkit")
    }

    implementation("com.github.technicallycoded:FoliaLib:main-SNAPSHOT")

    compileOnly(libs.cloudnet.driver) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    compileOnly(libs.cloudnet.bridge) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    compileOnly(libs.cloudnet.wrapper.jvm) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    compileOnly(libs.cloudnet.syncproxy) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
}

sourceSets {
    getByName("main") {
        java {
            srcDir("src/main/java")
        }
        kotlin {
            srcDir("src/main/kotlin")
        }
    }
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(21)
    }

    shadowJar {
        relocate("com.tcoded.folialib", "de.bypixeltv.skcloudnet.lib.folialib")
        archiveBaseName.set("SkCloudnet")
        archiveVersion.set(version.toString())
        archiveClassifier.set("")
    }
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

bukkit {
    main = "de.bypixeltv.skcloudnet.Main"

    version = versionString

    foliaSupported = true

    apiVersion = "1.21"

    authors = listOf("byPixelTV")

    website = "https://bypixeltv.de"

    description = "A Skript-Addon to interact with your CloudNet v4 instance."

    depend = listOf("CloudNet-Bridge", "Skript")

    prefix = "SkCloudnet"
}

kotlin {
    jvmToolchain(21)
}