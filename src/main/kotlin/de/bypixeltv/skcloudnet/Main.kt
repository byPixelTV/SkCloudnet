package de.bypixeltv.skcloudnet

import ch.njol.skript.Skript
import ch.njol.skript.SkriptAddon
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.plugin.java.JavaPlugin
import java.io.IOException

class Main : JavaPlugin() {

    private val miniMessages = MiniMessage.miniMessage()

    var instance: Main? = null
    var addon: SkriptAddon? = null

    @Suppress("Deprecation")
    override fun onEnable() {
        this.instance = this
        this.addon = Skript.registerAddon(this)
        val localAddon = this.addon
        try {
            localAddon?.loadClasses("de.bypixeltv.skcloudnet", "elements")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        server.consoleSender.sendMessage(miniMessages.deserialize("<blue>Successfully enabled SkCloudnet v1! <yellow>Good to see you :)</yellow></blue>"))
    }

    override fun onLoad() {
        server.consoleSender.sendMessage(miniMessages.deserialize("<blue>Loading SkCloudnet...</blue>"))
        CommandAPI.onLoad(CommandAPIBukkitConfig(this).silentLogs(true).verboseOutput(true))
    }

    override fun onDisable() {
        CommandAPI.onDisable()
        server.consoleSender.sendMessage(miniMessages.deserialize("<blue>Successfully disabled SkCloudnet v1! <yellow>Goodbye!</yellow></blue>"))
    }

    fun getMainInstance(): Main? {
        return instance
    }

    fun getAddonInstance(): SkriptAddon? {
        return addon
    }
}