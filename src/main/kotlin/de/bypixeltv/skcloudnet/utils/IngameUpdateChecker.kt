package de.bypixeltv.skcloudnet.utils

import com.tcoded.folialib.FoliaLib
import de.bypixeltv.skcloudnet.Main
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerJoinEvent
import java.util.function.Consumer

object IngameUpdateChecker {
    private val miniMessages = MiniMessage.miniMessage()

    private val foliaLib = FoliaLib(Main.INSTANCE)

    @Suppress("DEPRECATION", "UNUSED")
    val joinEvent = listen<PlayerJoinEvent> {
        val player = it.player
        if (Main.INSTANCE.config.getBoolean("update-checker")) {
            if (player.hasPermission("skcloudnet.admin.version") || player.isOp) {
                val currentVersion = Main.INSTANCE.description.version
                val updateVersion = UpdateChecker(Main.INSTANCE).getUpdateVersion(currentVersion)

                foliaLib.scheduler.runLater(Runnable {
                    updateVersion.thenApply { version ->
                        player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> update available: <green>$version</green>"))
                        player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> download at <aqua><click:open_url:'https://github.com/byPixelTV/SkCloudnet/releases'>https://github.com/byPixelTV/SkCloudnet/releases</click></aqua>"))
                        true
                    }
                }, 30)
            }
        }
        if (player.uniqueId.toString() == "4605c6c9-525f-4879-9642-48fab1468795") {
            player.sendMessage(miniMessages.deserialize("<dark_grey>[<gradient:aqua:blue:aqua>SkCloudnet</gradient>]</dark_grey> <green>SkCloudnet is installed on this server!</green>"))
        }
    }
}