package de.bypixeltv.skcloudnet.utils

import de.bypixeltv.skcloudnet.Main
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.player.PlayerJoinEvent
import org.spigotmc.SpigotConfig
import org.spigotmc.SpigotConfig.config

object UpdateChecker {
    private val miniMessages = MiniMessage.miniMessage()


    val joinEvent = listen<PlayerJoinEvent> {
        val player = it.player
        if (Main.INSTANCE.getConfig().getBoolean("update-checker")) {
            if (player.hasPermission("skcloudnet.admin.version") || player.isOp) {
                val githubVersion = GetVersion().getLatestAddonVersion()?.replace("v", "")?.toDouble()
                if (githubVersion != null) {
                    if (githubVersion > Main.INSTANCE.description.version.replace("v", "").toDouble()) {
                        player.sendMessage(miniMessages.deserialize("\n\n<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#43fa00>There is an update available for SkCloudnet!</color> <aqua>You're on version <yellow>${Main.INSTANCE.description.version}</yellow> and the latest version is <yellow>$githubVersion</yellow></aqua>!\n\n<color:#43fa00>Download the latest version here:</color> <blue>https://github.com/byPixelTV/SkCloudnet/releases</blue> <aqua>"))
                    } else {
                        if (githubVersion < Main.INSTANCE.description.version.replace("v", "").toDouble()) {
                            player.sendMessage(miniMessages.deserialize("\n\n<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#ff0000>You're running a development version of SkCloudnet! Please note that this version may contain bugs!</color> <aqua>Version <color:#ff0000>${Main.INSTANCE.description.version}</color> > <color:#43fa00>${GetVersion().getLatestAddonVersion()}</color></aqua>"))
                        }
                    }
                } else {
                    player.sendMessage(miniMessages.deserialize("\n\n<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#ff0000>Unable to fetch the latest version from Github!</color> <aqua>Are you rate limited?</aqua>"))
                }
            }
        }
    }
}