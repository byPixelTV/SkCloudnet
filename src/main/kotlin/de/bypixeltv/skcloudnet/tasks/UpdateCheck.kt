package de.bypixeltv.skcloudnet.tasks

import de.bypixeltv.skcloudnet.Main
import de.bypixeltv.skcloudnet.utils.GetVersion
import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.minimessage.MiniMessage

object UpdateCheck {
    private val miniMessages = MiniMessage.miniMessage()

    fun scheudle() {
        task(false, 36000, 72000) {
            val githubVersion = GetVersion().getLatestAddonVersion()?.replace("v", "")?.toDouble()
            if (githubVersion != null) {
                if (githubVersion > Main.INSTANCE.description.version.replace("v", "").toDouble()) {
                    Main.INSTANCE.server.consoleSender.sendMessage(" ")
                    Main.INSTANCE.server.consoleSender.sendMessage(" ")
                    Main.INSTANCE.server.consoleSender.sendMessage(miniMessages.deserialize("<color:#43fa00>There is an update available for SkCloudnet!</color> <aqua>You're on version <yellow>${Main.INSTANCE.description.version}</yellow> and the latest version is <yellow>$githubVersion</yellow></aqua>!\n\n<color:#43fa00>Download the latest version here:</color> <blue>https://github.com/byPixelTV/SkCloudnet/releases</blue> <aqua>"))
                    Main.INSTANCE.server.consoleSender.sendMessage(" ")
                    Main.INSTANCE.server.consoleSender.sendMessage(" ")
                } else {
                    if (githubVersion == Main.INSTANCE.description.version.replace("v", "").toDouble()) {
                        Main.INSTANCE.server.consoleSender.sendMessage(" ")
                        Main.INSTANCE.server.consoleSender.sendMessage(" ")
                        Main.INSTANCE.server.consoleSender.sendMessage(miniMessages.deserialize("<color:#43fa00>You're on the latest version of SkCloudnet!</color> <aqua>Version <yellow>${Main.INSTANCE.description.version}</yellow></aqua>"))
                        Main.INSTANCE.server.consoleSender.sendMessage(" ")
                        Main.INSTANCE.server.consoleSender.sendMessage(" ")
                    } else if (githubVersion < Main.INSTANCE.description.version.replace("v", "").toDouble()) {
                        Main.INSTANCE.server.consoleSender.sendMessage(" ")
                        Main.INSTANCE.server.consoleSender.sendMessage(" ")
                        Main.INSTANCE.server.consoleSender.sendMessage(miniMessages.deserialize("<color:#ff0000>You're running a development version of SkCloudnet! Please note that this version may contain bugs!</color> <aqua>Version <color:#ff0000>${Main.INSTANCE.description.version}</color> > <color:#43fa00>${GetVersion().getLatestAddonVersion()}</color></aqua>"))
                        Main.INSTANCE.server.consoleSender.sendMessage(" ")
                        Main.INSTANCE.server.consoleSender.sendMessage(" ")
                    }
                }
            } else {
                Main.INSTANCE.server.consoleSender.sendMessage(" ")
                Main.INSTANCE.server.consoleSender.sendMessage(" ")
                Main.INSTANCE.server.consoleSender.sendMessage(miniMessages.deserialize("<color:#ff0000>Unable to fetch the latest version from Github!</color> <aqua>Are you rate limited?</aqua>"))
                Main.INSTANCE.server.consoleSender.sendMessage(" ")
                Main.INSTANCE.server.consoleSender.sendMessage(" ")
            }
        }
    }

    init {
        scheudle()
    }

}