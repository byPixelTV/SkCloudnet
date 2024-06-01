plugins {
    kotlin("jvm") version "2.0.0"
    id("io.papermc.paperweight.userdev") version "1.7.1"
    id("xyz.jpenilla.run-paper") version "1.1.0"
}

group = "de.bypixeltv"
version = "1.7.5"

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
    paperweight.paperDevBundle("1.20.6-R0.1-SNAPSHOT")
    implementation("dev.jorel", "commandapi-bukkit-shade-mojang-mapped", "9.4.2")
    implementation("dev.jorel", "commandapi-bukkit-kotlin", "9.4.2")
    implementation("net.axay:kspigot:1.20.4")

    implementation("com.github.SkriptLang:Skript:2.9.0-beta1-pre")


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
    named("compileKotlin", org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask::class.java) {
        compilerOptions {
            freeCompilerArgs.add("-Xexport-kdoc")
        }
    }
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

kotlin {
    jvmToolchain(21)
}