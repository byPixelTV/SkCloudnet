plugins {
    kotlin("jvm") version "2.0.0"
    id("io.papermc.paperweight.userdev") version "1.7.1"
}

group = "de.bypixeltv"
version = "1.7.6"

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
    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")
    implementation("dev.jorel", "commandapi-bukkit-shade-mojang-mapped", "9.5.1")
    implementation("dev.jorel", "commandapi-bukkit-kotlin", "9.5.1")
    implementation("net.axay:kspigot:1.20.4")

    implementation("com.github.SkriptLang:Skript:2.9.0")


    implementation("eu.cloudnetservice.cloudnet:syncproxy:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:bridge:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:driver:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:platform-inject-runtime:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:platform-inject-processor:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:platform-inject-loader:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:platform-inject-api:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:platform-inject-support:4.0.0-RC10")
    implementation("eu.cloudnetservice.cloudnet:common:4.0.0-RC10")
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

kotlin {
    jvmToolchain(21)
}