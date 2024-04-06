plugins {
    kotlin("jvm") version "1.9.23"
    id("io.papermc.paperweight.userdev") version "1.5.12"
    id("xyz.jpenilla.run-paper") version "1.1.0"
}

group = "de.bypixeltv"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        url = uri("https://repo.skriptlang.org/releases")
    }
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    implementation("dev.jorel", "commandapi-bukkit-shade", "9.3.0")
    implementation("dev.jorel", "commandapi-bukkit-kotlin", "9.3.0")
    implementation("net.axay:kspigot:1.20.3")

    implementation("com.github.SkriptLang:Skript:2.8.4")


    implementation("eu.cloudnetservice.cloudnet:syncproxy:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:bridge:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:driver:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:platform-inject-runtime:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:platform-inject-processor:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:platform-inject-loader:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:platform-inject-api:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:platform-inject-support:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:common:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:node:4.0.0-RC10")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}

kotlin {
    jvmToolchain(17)
}