plugins {
    kotlin("jvm") version "2.0.20"
    id("io.papermc.paperweight.userdev") version "1.7.3"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

group = "de.bypixeltv"
version = "1.7.7"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.skriptlang.org/releases")
    }
}

dependencies {
    paperweight.paperDevBundle("1.21.1-R0.1-SNAPSHOT")

    bukkitLibrary(libs.kspigot) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    bukkitLibrary(libs.commandapi.kotlin) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    bukkitLibrary(libs.commandapi.shade) {
        exclude(group = "org.bukkit", module = "bukkit")
    }

    implementation(libs.skript) {
        exclude(group = "org.bukkit", module = "bukkit")
    }


    implementation(libs.cloudnet.driver) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    implementation(libs.cloudnet.bridge) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    implementation(libs.cloudnet.wrapper.jvm) {
        exclude(group = "org.bukkit", module = "bukkit")
    }
    implementation(libs.cloudnet.syncproxy) {
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
        options.compilerArgs.add("-Xlint:deprecation")
    }
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

bukkit {
    main = "de.bypixeltv.skcloudnet.Main"

    foliaSupported = false

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