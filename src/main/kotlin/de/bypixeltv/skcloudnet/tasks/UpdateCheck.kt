package de.bypixeltv.skcloudnet.tasks

import de.bypixeltv.skcloudnet.Main
import de.bypixeltv.skcloudnet.utils.VersionUtils
import net.axay.kspigot.extensions.server
import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.minimessage.MiniMessage

object UpdateCheck {
    private val miniMessages = MiniMessage.miniMessage()

    @Suppress("DEPRECATION")
    private fun schedule() {
        task(false, 36000, 72000) {
            val githubVersion = VersionUtils().getLatestAddonVersion()?.replace("v", "")
            val currentVersion = Main.INSTANCE.description.version.replace("v", "")

            if (githubVersion != null) {
                if (VersionUtils().isVersionGreater(githubVersion, currentVersion)) {
                    // There is an update available
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(" ")
                    // You're on the latest version
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(miniMessages.deserialize("<grey>[<aqua>SkCloudnet</aqua>]</grey> <color:#43fa00>You're on the latest version of SkCloudnet!</color> <aqua>Version <yellow>v${Main.INSTANCE.description.version}</yellow></aqua>"))
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(" ")
                } else if (githubVersion == currentVersion) {
                    // You're running a development version
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(miniMessages.deserialize("<grey>[<aqua>SkCloudnet</aqua>]</grey> <color:#43fa00>There is an update available for SkCloudnet!</color> <aqua>You're on version <yellow>v${Main.INSTANCE.description.version}</yellow> and the latest version is <yellow>$githubVersion</yellow></aqua>!\n\n<color:#43fa00>Download the latest version here:</color> <blue>https://github.com/byPixelTV/SkCloudnet/releases</blue> <aqua>"))
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(" ")
                } else {
                    server.consoleSender.sendMessage(miniMessages.deserialize("<grey>[<aqua>SkCloudnet</aqua>]</grey> <color:#ff0000>You're running a development version of SkCloudnet! Please note that this version may contain bugs!</color> <aqua>Version <color:#ff0000>v${Main.INSTANCE.description.version}</color> > <color:#43fa00>${VersionUtils().getLatestAddonVersion()}</color></aqua>"))
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(" ")
                }
            } else {
                // Unable to fetch the latest version from GitHub
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(miniMessages.deserialize("<grey>[<aqua>SkCloudnet</aqua>]</grey> <color:#ff0000>Unable to fetch the latest version from Github!</color> <aqua>Are you rate limited?</aqua>"))
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(" ")
            }
        }
    }

    init {
        schedule()
    }

}