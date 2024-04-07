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
                val githubVersion = GetVersion().getLatestAddonVersion()
                if (githubVersion != null) {
                    if (githubVersion != Main.INSTANCE.getPluginVersion()) {
                        player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#43fa00>There is an update available for SkCloudnet!</color> <aqua>You're on version <yellow>${Main.INSTANCE.getPluginVersion()}</yellow> and the latest version is <yellow>$githubVersion</yellow></aqua>!\n" +
                                "\n" +
                                "<color:#43fa00>Download the latest version here:</color> <click:open_url:'https://github.com/byPixelTV/SkCloudnet/releases'><blue>https://github.com/byPixelTV/SkCloudnet/releases</blue></click> <aqua>"))
                    } else {
                        return@listen
                    }
                } else {
                    player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <color:#ff0000>Unable to fetch the latest version from Github!</color> <aqua>Are you rate limited?</aqua>"))
                }
            }
        }
    }
}