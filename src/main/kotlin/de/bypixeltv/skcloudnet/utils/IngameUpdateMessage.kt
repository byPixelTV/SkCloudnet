package de.bypixeltv.skcloudnet.utils

import de.bypixeltv.skcloudnet.Main
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.player.PlayerJoinEvent

object IngameUpdateMessage {
    private val miniMessages = MiniMessage.miniMessage()

    @Suppress("DEPRECATION", "UNUSED")
    val joinEvent = listen<PlayerJoinEvent> {
        val player = it.player
        if (Main.INSTANCE.config.getBoolean("update-checker")) {
            if (player.hasPermission("skcloudnet.admin.version") || player.isOp) {
                val githubVersion = VersionUtils().getLatestAddonVersion()?.replace("v", "")
                val currentVersion = Main.INSTANCE.description.version.replace("v", "")

                if (githubVersion != null) {
                    if (VersionUtils().isVersionGreater(githubVersion, currentVersion)) {
                        player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#43fa00>There is an update available for SkCloudnet!</color> <aqua>You're on version <yellow>${Main.INSTANCE.description.version}</yellow> and the latest version is <yellow>$githubVersion</yellow></aqua>!<color:#43fa00>Download the latest version here:</color> <blue>https://github.com/byPixelTV/SkCloudnet/releases</blue> <aqua>"))
                    } else if (githubVersion == currentVersion) {
                        return@listen
                    } else {
                        player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#ff0000>You're running a development version of SkCloudnet! Please note that this version may contain bugs!</color> <aqua>Version <color:#ff0000>${Main.INSTANCE.description.version}</color> > <color:#43fa00>${VersionUtils().getLatestAddonVersion()}</color></aqua>"))
                    }
                } else {
                    player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#ff0000>Unable to fetch the latest version from Github!</color> <aqua>Are you rate limited?</aqua>"))
                }
            }
        }
    }
}