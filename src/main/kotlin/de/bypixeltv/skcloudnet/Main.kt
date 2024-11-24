package de.bypixeltv.skcloudnet

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import de.bypixeltv.skcloudnet.commands.SkCloudnetCommands
import de.bypixeltv.skcloudnet.utils.IngameUpdateChecker
import de.bypixeltv.skcloudnet.utils.UpdateChecker
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
            private set
    }

    override fun load() {
        INSTANCE = this
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true).verboseOutput(true))
        SkCloudnetCommands()
    }

    @Suppress("DEPRECATION")
    override fun startup() {
        saveDefaultConfig()

        this.instance = this
        this.addon = Skript.registerAddon(this)
        val localAddon = this.addon
        try {
            localAddon?.loadClasses("de.bypixeltv.skcloudnet", "elements")
        } catch (e: IOException) {
            e.printStackTrace()
        }

        server.consoleSender.sendMessage(miniMessages.deserialize("<grey>[<aqua>SkCloudnet</aqua>]</grey> <aqua>Successfully enabled SkCloudnet v${this.description.version}!</aqua>"))

        IngameUpdateChecker

        val version = description.version
        if (version.contains("-")) {
            server.consoleSender.sendMessage(miniMessages.deserialize("<yellow>This is a BETA build, things may not work as expected, please report any bugs on GitHub</yellow>"))
            server.consoleSender.sendMessage(miniMessages.deserialize("<yellow>https://github.com/byPixelTV/SkCloudnet/issues</yellow>"))
        }

        UpdateChecker.checkForUpdate(version)

        Metrics(this, 21526)

        // Check if server plugin cloudnet-bridge is installed
        val pluginManager = server.pluginManager
        val cloudnetBridgePlugin = pluginManager.getPlugin("cloudnet-bridge")

        if (cloudnetBridgePlugin == null) {
            server.consoleSender.sendMessage(miniMessages.deserialize("<red>CloudNet-Bridge is not installed, please install it to use SkCloudnet</red>"))
            server.pluginManager.disablePlugin(this)
        }
    }

    @Suppress("DEPRECATION")
    override fun shutdown() {
        CommandAPI.onDisable()
    }
}
