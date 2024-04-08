package de.bypixeltv.skcloudnet

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import de.bypixeltv.skcloudnet.commands.SkCloudnetCommands
import de.bypixeltv.skcloudnet.tasks.UpdateCheck
import de.bypixeltv.skcloudnet.utils.GetVersion
import de.bypixeltv.skcloudnet.utils.UpdateChecker
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.axay.kspigot.event.listen
import net.axay.kspigot.main.KSpigot
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.event.player.PlayerJoinEvent
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

        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#43fa00>Enabling SkCloudnet ${this.description.version}...</color>"))
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
        server.consoleSender.sendMessage(miniMessages.deserialize("<aqua>Successfully enabled SkCloudnet ${this.description.version}!</aqua>"))


        val githubVersion = GetVersion().getLatestAddonVersion()?.replace("v", "")?.toDouble()
        if (githubVersion != null) {
            if (githubVersion > this.description.version.replace("v", "").toDouble()) {
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(miniMessages.deserialize("<color:#43fa00>There is an update available for SkCloudnet!</color> <aqua>You're on version <yellow>${this.description.version}</yellow> and the latest version is <yellow>$githubVersion</yellow></aqua>!\n\n<color:#43fa00>Download the latest version here:</color> <blue>https://github.com/byPixelTV/SkCloudnet/releases</blue> <aqua>"))
                server.consoleSender.sendMessage(" ")
                server.consoleSender.sendMessage(" ")
            } else {
                if (githubVersion == this.description.version.replace("v", "").toDouble()) {
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(miniMessages.deserialize("<color:#43fa00>You're on the latest version of SkCloudnet!</color> <aqua>Version <yellow>${this.description.version}</yellow></aqua>"))
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(" ")
                } else if (githubVersion < this.description.version.replace("v", "").toDouble()) {
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(miniMessages.deserialize("<color:#ff0000>You're running a development version of SkCloudnet! Please note that this version may contain bugs!</color> <aqua>Version <color:#ff0000>${this.description.version}</color> > <color:#43fa00>${GetVersion().getLatestAddonVersion()}</color></aqua>"))
                    server.consoleSender.sendMessage(" ")
                    server.consoleSender.sendMessage(" ")
                }
            }
        } else {
            server.consoleSender.sendMessage(" ")
            server.consoleSender.sendMessage(" ")
            server.consoleSender.sendMessage(miniMessages.deserialize("<color:#ff0000>Unable to fetch the latest version from Github!</color> <aqua>Are you rate limited?</aqua>"))
            server.consoleSender.sendMessage(" ")
            server.consoleSender.sendMessage(" ")
        }

        UpdateChecker
        UpdateCheck

        val metrics: Metrics = Metrics(this, 21526)
    }

    override fun load() {
        server.consoleSender.sendMessage(miniMessages.deserialize("<blue>Loading SkCloudnet...</blue>"))
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true).verboseOutput(true))
        SkCloudnetCommands()
    }

    override fun shutdown() {
        CommandAPI.onDisable()
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#ff0000>Disabling SkCloudnet ${this.description.version}...</color>"))
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
        server.consoleSender.sendMessage(miniMessages.deserialize("<aqua>Successfully disabled SkCloudnet ${this.description.version}!</aqua>"))
    }

    fun getMainInstance(): Main? {
        return instance
    }

    fun getAddonInstance(): SkriptAddon? {
        return addon
    }
}