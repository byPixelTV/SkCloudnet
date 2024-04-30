package de.bypixeltv.skcloudnet

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import de.bypixeltv.skcloudnet.commands.SkCloudnetCommands
import de.bypixeltv.skcloudnet.tasks.UpdateCheck
import de.bypixeltv.skcloudnet.utils.VersionUtils
import de.bypixeltv.skcloudnet.utils.IngameUpdateMessage
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.axay.kspigot.main.KSpigot
import net.kyori.adventure.text.minimessage.MiniMessage
import java.io.IOException

class Main : KSpigot() {

    private val miniMessages = MiniMessage.miniMessage()

    var instance: Main? = null
    private var addon: SkriptAddon? = null

    companion object {
        lateinit var INSTANCE: Main
    }

    @Suppress("DEPRECATION")
    override fun startup() {
        saveDefaultConfig()

        INSTANCE = this
        this.instance = this
        this.addon = Skript.registerAddon(this)
        val localAddon = this.addon
        try {
            localAddon?.loadClasses("de.bypixeltv.skcloudnet", "elements")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        server.consoleSender.sendMessage(" ")
        server.consoleSender.sendMessage(" ")
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#43fa00> _____  _     _____  _                    _               _  </color>"))
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#43fa00>/  ___|| |   /  __ \\| |                  | |             | | </color>"))
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#43fa00>\\ `--. | | __| /  \\/| |  ___   _   _   __| | _ __    ___ | |_</color>"))
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#43fa00> `--. \\| |/ /| |    | | / _ \\ | | | | / _` || '_ \\  / _ \\| __|</color>"))
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#43fa00>/\\__/ /|   < | \\__/\\| || (_) || |_| || (_| || | | ||  __/| |_ </color>"))
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#43fa00>\\____/ |_|\\_\\ \\____/|_| \\___/  \\__,_| \\__,_||_| |_| \\___| \\__|</color>"))
        server.consoleSender.sendMessage(miniMessages.deserialize("<yellow>Made by byPixelTV</yellow>"))
        server.consoleSender.sendMessage(" ")
        server.consoleSender.sendMessage(" ")
        server.consoleSender.sendMessage(miniMessages.deserialize("<grey>[<aqua>SkCloudnet</aqua>]</grey> <aqua>Successfully enabled SkCloudnet v${this.description.version}!</aqua>"))


        val githubVersion = VersionUtils().getLatestAddonVersion()?.replace("v", "")
        val currentVersion = this.description.version.replace("v", "")

        if (githubVersion != null) {
            if (VersionUtils().isVersionGreater(githubVersion, currentVersion)) {
                // There is an update available
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(" ")
                // You're on the latest version
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(miniMessages.deserialize("<grey>[<aqua>SkCloudnet</aqua>]</grey> <color:#43fa00>You're on the latest version of SkCloudnet!</color> <aqua>Version <yellow>v${this.description.version}</yellow></aqua>"))
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(" ")
            } else if (githubVersion == currentVersion) {
                // You're running a development version
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(miniMessages.deserialize("<grey>[<aqua>SkCloudnet</aqua>]</grey> <color:#43fa00>There is an update available for SkCloudnet!</color> <aqua>You're on version <yellow>v${this.description.version}</yellow> and the latest version is <yellow>$githubVersion</yellow></aqua>!\n\n<color:#43fa00>Download the latest version here:</color> <blue>https://github.com/byPixelTV/SkCloudnet/releases</blue> <aqua>"))
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(" ")
            } else {
                    server.consoleSender.sendMessage(miniMessages.deserialize("<grey>[<aqua>SkCloudnet</aqua>]</grey> <color:#ff0000>You're running a development version of SkCloudnet! Please note that this version may contain bugs!</color> <aqua>Version <color:#ff0000>v${this.description.version}</color> > <color:#43fa00>${VersionUtils().getLatestAddonVersion()}</color></aqua>"))
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

        IngameUpdateMessage
        UpdateCheck

        Metrics(this, 21526)

        // Check if server plugin cloudnet-bridge is installed
        val pluginManager = server.pluginManager
        val cloudnetBridgePlugin = pluginManager.getPlugin("cloudnet-bridge")

        if (cloudnetBridgePlugin == null) {
            // The cloudnet-bridge plugin isn't installed
            server.consoleSender.sendMessage(miniMessages.deserialize("<grey>[<aqua>SkCloudnet</aqua>]</grey> <color:#ff0000>Could not find CloudNet-Bridge, disabling!"))
            pluginManager.disablePlugin(this)
        }

    }

    override fun load() {
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true).verboseOutput(true))
        SkCloudnetCommands()
    }

    @Suppress("DEPRECATION")
    override fun shutdown() {
        CommandAPI.onDisable()
        server.consoleSender.sendMessage(" ")
        server.consoleSender.sendMessage(" ")
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#ff0000> _____  _     _____  _                    _               _  </color>"))
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#ff0000>/  ___|| |   /  __ \\| |                  | |             | | </color>"))
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#ff0000>\\ `--. | | __| /  \\/| |  ___   _   _   __| | _ __    ___ | |_</color>"))
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#ff0000> `--. \\| |/ /| |    | | / _ \\ | | | | / _` || '_ \\  / _ \\| __|</color>"))
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#ff0000>/\\__/ /|   < | \\__/\\| || (_) || |_| || (_| || | | ||  __/| |_ </color>"))
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#ff0000>\\____/ |_|\\_\\ \\____/|_| \\___/  \\__,_| \\__,_||_| |_| \\___| \\__|</color>"))
        server.consoleSender.sendMessage(miniMessages.deserialize("<yellow>Made by byPixelTV</yellow>"))
        server.consoleSender.sendMessage(" ")
        server.consoleSender.sendMessage(" ")
        server.consoleSender.sendMessage(miniMessages.deserialize("<grey>[<aqua>SkCloudnet</aqua>]</grey> <aqua>Successfully disabled SkCloudnet v${this.description.version}!</aqua>"))
    }
}