package de.bypixeltv.skcloudnet

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import de.bypixeltv.skcloudnet.command.InfoCommands
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException

class Main : JavaPlugin() {

    private val miniMessages = MiniMessage.miniMessage()

    var instance: Main? = null
    private var addon: SkriptAddon? = null

    override fun onEnable() {
        this.instance = this
        this.addon = Skript.registerAddon(this)
        val localAddon = this.addon
        try {
            localAddon?.loadClasses("de.bypixeltv.skcloudnet", "elements")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        InfoCommands()
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#43fa00>Enabling SkCloudnet v1...</color>"))
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
        server.consoleSender.sendMessage(miniMessages.deserialize("<aqua>Successfully enabled SkCloudnet v1!</aqua>"))
    }

    override fun onLoad() {
        server.consoleSender.sendMessage(miniMessages.deserialize("<blue>Loading SkCloudnet...</blue>"))
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true).verboseOutput(true))
    }

    override fun onDisable() {
        CommandAPI.onDisable()
        server.consoleSender.sendMessage(miniMessages.deserialize("<color:#ff0000>Disabling SkCloudnet v1...</color>"))
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
        server.consoleSender.sendMessage(miniMessages.deserialize("<aqua>Successfully disabled SkCloudnet v1!</aqua>"))
    }

    fun getMainInstance(): Main? {
        return instance
    }

    fun getAddonInstance(): SkriptAddon? {
        return addon
    }
}