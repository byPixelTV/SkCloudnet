plugins {
    kotlin("jvm") version "1.9.23"
    id("io.papermc.paperweight.userdev") version "1.5.12"
    id("xyz.jpenilla.run-paper") version "1.1.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

group = "de.bypixeltv"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
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
    implementation("com.github.SkriptLang:Skript:2.8.3")
    library(kotlin("stdlib"))
}

kotlin {
    jvmToolchain(17)
}