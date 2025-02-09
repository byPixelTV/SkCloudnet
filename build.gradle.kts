plugins {
    kotlin("jvm") version "2.1.10"
    id("io.papermc.paperweight.userdev") version "2.0.0-SNAPSHOT"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.gradleup.shadow") version "8.3.5"
}

val versionString = "1.7.8-Beta"

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
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")

    bukkitLibrary(libs.kspigot)
    bukkitLibrary(libs.commandapi.kotlin)
    bukkitLibrary(libs.commandapi.shade)

    compileOnly(libs.skript)

    implementation("com.github.technicallycoded:FoliaLib:main-SNAPSHOT")

    compileOnly(libs.cloudnet.driver)
    compileOnly(libs.cloudnet.bridge)
    compileOnly(libs.cloudnet.wrapper.jvm)
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
        options.release.set(23)
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

    website = "https://github.com/byPixelTV/SkCloudnet"

    description = "A Skript-Addon to interact with your CloudNet v4 instance."

    depend = listOf("CloudNet-Bridge", "Skript")

    prefix = "SkCloudnet"
}

kotlin {
    jvmToolchain(23)
}
