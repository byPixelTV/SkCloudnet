package de.bypixeltv.skcloudnet.utils

import de.bypixeltv.skcloudnet.Main
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.player.PlayerJoinEvent

object UpdateChecker {
    private val miniMessages = MiniMessage.miniMessage()


    val joinEvent = listen<PlayerJoinEvent> {
        val player = it.player
        if (player.hasPermission("skcloudnet.updatechecker")) {
            val githubVersion = GetVersion().getLatestGithubAddonVersion()
            if (githubVersion != null) {
                if (githubVersion != Main.INSTANCE.getPluginVersion()) {
                    player.sendMessage(" ")
                    player.sendMessage(" ")
                    player.sendMessage(miniMessages.deserialize("<color:#43fa00>There is an update available for SkCloudnet!</color> <aqua>You're on version <yellow>${Main.INSTANCE.getPluginVersion()}</yellow> and the latest version is <yellow>$githubVersion</yellow></aqua>!\n\n<color:#43fa00>Download the latest version here:</color> <blue>https://github.com/byPixelTV/SkCloudnet/releases</blue> <aqua>"))
                    player.sendMessage(" ")
                    player.sendMessage(" ")
                } else {
                    return@listen
                }
            } else {
                player.sendMessage(" ")
                player.sendMessage(" ")
                player.sendMessage(miniMessages.deserialize("<color:#ff0000>Unable to fetch the latest version from Github!</color> <aqua>Are you rate limited?</aqua>"))
                player.sendMessage(" ")
                player.sendMessage(" ")
            }
        }
    }
}