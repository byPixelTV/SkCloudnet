package de.bypixeltv.skcloudnet.commands

import ch.njol.skript.Skript
import de.bypixeltv.skcloudnet.Main
import de.bypixeltv.skcloudnet.tasks.UpdateCheck
import de.bypixeltv.skcloudnet.utils.GetVersion
import de.bypixeltv.skcloudnet.utils.UpdateChecker
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.ServiceTaskProvider
import eu.cloudnetservice.driver.service.ServiceTask
import net.axay.kspigot.config.JsonConfigManager.saveConfig
import net.kyori.adventure.text.minimessage.MiniMessage
import java.nio.file.Files
import java.nio.file.Paths

class SkCloudnetCommands {
    private val miniMessages = MiniMessage.miniMessage()

    val command = commandTree("skcloudnet") {
        withPermission("skcloudnet.admin")
        literalArgument("info") {
            withPermission("skcloudnet.admin.info")
            playerExecutor { player, _ ->
                val addonMessages = Skript.getAddons().mapNotNull { addon ->
                    val name = addon.name
                    if (!name.contains("SkCloudnet")) {
                        "<grey>-</grey> <aqua>$name</aqua> <yellow>v${addon.plugin.description.version}</yellow>"
                    } else {
                        null
                    }
                }

                val addonsList = if (addonMessages.isNotEmpty()) addonMessages.joinToString("\n") else "<color:#ff0000>No other addons found</color>"
                player.sendMessage(
                    miniMessages.deserialize(
                        "<dark_grey>--- <aqua>SkCloudnet</aqua> <grey>Info:</grey> ---</dark_grey>\n\n<grey>SkCloudnet Version: <aqua>${Main.INSTANCE.description.version}</aqua>\nSkript Version: <aqua>${GetVersion().getSkriptVersion()}</aqua>\nServer Version: <aqua>${Main.INSTANCE.server.minecraftVersion}</aqua>\nServer Implementation: <aqua>${Main.INSTANCE.server.version}</aqua>\nAddons:\n$addonsList</grey>"
                    )
                )
            }
        }
        literalArgument("docs") {
            withPermission("skcloudnet.admin.docs")
            playerExecutor { player, _ ->
                player.sendMessage(
                    miniMessages.deserialize(
                        "<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <grey><aqua>Documentation</aqua> for <aqua>SkCloudnet:</aqua></grey>\n<grey>-</grey> <click:open_url:'https://skripthub.net/docs/?addon=SkCloudnet'><aqua>SkriptHub</aqua> <dark_grey>(<aqua>Click me!</aqua>)</dark_grey></click>\n<grey>-</grey> <click:open_url:'https://docs.skunity.com/syntax/search/addon:skcloudnet'><aqua>SkUnity</aqua> <dark_grey>(<aqua>Click me!</aqua>)</dark_grey></click>"
                    )
                )
            }
        }
        literalArgument("version") {
            withPermission("skcloudnet.admin.version")
            playerExecutor { player, _ ->
                val githubVersion = GetVersion().getLatestAddonVersion()?.replace("v", "")?.toDouble()
                if (githubVersion != null) {
                    if (githubVersion > Main.INSTANCE.description.version.replace("v", "").toDouble()) {
                        player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#43fa00>There is an update available for SkCloudnet!</color> <aqua>You're on version <yellow>${Main.INSTANCE.description.version}</yellow> and the latest version is <yellow>$githubVersion</yellow></aqua>!\n\n<color:#43fa00>Download the latest version here:</color> <blue>https://github.com/byPixelTV/SkCloudnet/releases</blue> <aqua>"))
                    } else {
                        if (githubVersion == Main.INSTANCE.description.version.replace("v", "").toDouble()) {
                            player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#43fa00>You're on the latest version of SkCloudnet!</color> <aqua>Version <yellow>${Main.INSTANCE.description.version}</yellow></aqua>"))
                        } else if (githubVersion < Main.INSTANCE.description.version.replace("v", "").toDouble()) {
                            player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#ff0000>You're running a development version of SkCloudnet! Please note that this version may contain bugs!</color> <aqua>Version <color:#ff0000>${Main.INSTANCE.description.version}</color> > <color:#43fa00>${GetVersion().getLatestAddonVersion()}</color></aqua>"))
                        }
                    }
                } else {
                    player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#ff0000>Unable to fetch the latest version from Github!</color> <aqua>Are you rate limited?</aqua>"))
                }
            }
        }
        literalArgument("reload") {
            withPermission("skcloudnet.admin.reload")
            playerExecutor { player, _ ->
                Main.INSTANCE.reloadConfig()
                val path = Paths.get("/plugins/SkCloudnet/config.yml")
                if (Files.exists(path)) {
                    Main.INSTANCE.saveConfig()
                } else {
                    Main.INSTANCE.saveDefaultConfig()
                }
                player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#43fa00>Successfully reloaded the config!</color>"))
            }
        }
    }
}