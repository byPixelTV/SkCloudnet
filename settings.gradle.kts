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
            val paperVersion = "1.21.1-R0.1-SNAPSHOT"
            val jedisVersion = "5.2.0-beta5"
            val kSpigotVersion = "1.21.0"
            val commandAPIVersion = "9.5.3"
            val coroutinesCoreVersion = "1.9.0"
            val jsonVersion = "20240303"
            val cloudnetVersion = "4.0.0-RC10"
            val skriptVersion = "2.9.2"

            library("paper", "io.papermc.paper:paper-api:$paperVersion")
            library("jedis", "redis.clients:jedis:$jedisVersion")
            library("kspigot", "net.axay:kspigot:$kSpigotVersion")
            library("paperweight", "io.papermc.paperweight:paperweight-gradle-plugin:$paperVersion")
            library("commandapi-shade", "dev.jorel:commandapi-bukkit-shade-mojang-mapped:$commandAPIVersion")
            library("commandapi-kotlin", "dev.jorel:commandapi-bukkit-kotlin:$commandAPIVersion")
            library("coroutines", "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesCoreVersion")
            library("cloudnet-driver", "eu.cloudnetservice.cloudnet:driver:$cloudnetVersion")
            library("cloudnet-bridge", "eu.cloudnetservice.cloudnet:bridge:$cloudnetVersion")
            library("cloudnet-wrapper-jvm", "eu.cloudnetservice.cloudnet:wrapper-jvm:$cloudnetVersion")
            library("cloudnet-syncproxy", "eu.cloudnetservice.cloudnet:syncproxy:$cloudnetVersion")
            library("json", "org.json:json:$jsonVersion")
            library("skript", "com.github.SkriptLang:Skript:$skriptVersion")
        }
    }
}