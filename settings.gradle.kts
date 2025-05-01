pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "SkCloudnet"

dependencyResolutionManagement {

    versionCatalogs {
        create("libs") {
            val paperVersion = "1.21.4-R0.1-SNAPSHOT"
            val kSpigotVersion = "1.21.0"
            val commandAPIVersion = "10.0.0"
            val coroutinesCoreVersion = "1.10.2"
            val jsonVersion = "20250107"
            val skriptVersion = "2.11.1"

            val cloudnetVersion = "4.0.0-RC11.2"

            library("paper", "io.papermc.paper:paper-api:$paperVersion")
            library("kspigot", "net.axay:kspigot:$kSpigotVersion")
            library("paperweight", "io.papermc.paperweight:paperweight-gradle-plugin:$paperVersion")
            library("commandapi-shade", "dev.jorel:commandapi-bukkit-shade-mojang-mapped:$commandAPIVersion")
            library("commandapi-kotlin", "dev.jorel:commandapi-bukkit-kotlin:$commandAPIVersion")
            library("coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesCoreVersion")
            library("json", "org.json:json:$jsonVersion")
            library("skript", "com.github.SkriptLang:Skript:$skriptVersion")

            library("cloudnet-driver", "eu.cloudnetservice.cloudnet:driver:$cloudnetVersion")
            library("cloudnet-bridge", "eu.cloudnetservice.cloudnet:bridge:4.0.0-RC10")
            library("cloudnet-wrapper-jvm", "eu.cloudnetservice.cloudnet:wrapper-jvm:$cloudnetVersion")
            library("cloudnet-syncproxy", "eu.cloudnetservice.cloudnet:syncproxy:$cloudnetVersion")
        }
    }
}