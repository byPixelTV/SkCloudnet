package de.bypixeltv.skcloudnet.utils

import de.bypixeltv.skcloudnet.Main
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent

object IngameUpdateChecker {
    private val miniMessages = MiniMessage.miniMessage()

    @Suppress("DEPRECATION", "UNUSED")
    val joinEvent = listen<PlayerJoinEvent> {
        val player = it.player
        if (Main.INSTANCE.config.getBoolean("update-checker")) {
            if (player.hasPermission("skcloudnet.admin.version") || player.isOp) {
                val currentVersion = Main.INSTANCE.description.version
                val updateVersion = UpdateChecker(Main.INSTANCE).getUpdateVersion(currentVersion)

                Bukkit.getScheduler().runTaskLater(Main.INSTANCE, Runnable {
                    updateVersion.thenApply { version ->
                        player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> update available: <green>$version</green>"))
                        player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> download at <aqua><click:open_url:'https://github.com/byPixelTV/SkCloudnet/releases'>https://github.com/byPixelTV/SkCloudnet/releases</click></aqua>"))
                        true
                    }
                }, 30)
            }
        }
    }
}