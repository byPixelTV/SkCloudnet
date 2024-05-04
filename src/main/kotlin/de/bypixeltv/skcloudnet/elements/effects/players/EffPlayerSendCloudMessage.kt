package de.bypixeltv.skcloudnet.elements.effects.players

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import de.bypixeltv.skcloudnet.Main
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.registry.ServiceRegistry
import eu.cloudnetservice.modules.bridge.player.CloudPlayer
import eu.cloudnetservice.modules.bridge.player.PlayerManager
import eu.cloudnetservice.modules.bridge.player.executor.PlayerExecutor
import net.axay.kspigot.chat.literalText
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.jetbrains.annotations.Nullable
import java.util.*


@Name("Kick Player From Cloud")
@Description("Kicks a player from the CloudNet network.")
@Examples("kick \"byPixelTV\" parsed as player from proxy due to \"Star SkCloudnet now!!!\"")
@Since("1.4")

class EffPlayerSendCloudMessage : Effect() {

    private val serviceRegistry: ServiceRegistry? = InjectionLayer.ext().instance(ServiceRegistry::class.java)
    private val playerManager: PlayerManager? = serviceRegistry?.firstProvider(PlayerManager::class.java)

    companion object{
        init {
            Skript.registerEffect(EffPlayerSendCloudMessage::class.java, "send (cloud|cloudnet|bungee|velocity|proxy|bungeecord) (message|msg) %string% to [(the player[s]|player)] %strings%")
        }
    }

    private var message: Expression<String>? = null
    @Nullable
    private var uuids: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.message = expressions[0] as Expression<String>
        this.uuids = expressions[1] as Expression<String>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "send cloud msg ${this.message} to player ${this.uuids}"
    }

    override fun execute(e: Event?) {
        val msg = message?.getSingle(e)
        for (p in uuids!!.getArray(e)) {
            val uuidRegex = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"
            val uuidMatchResult = uuidRegex.toRegex().find(p.toString())

            if (uuidMatchResult != null) {
                val uuidString = uuidMatchResult.value
                val uuid = UUID.fromString(uuidString)
                playerManager?.playerExecutor(uuid)?.sendChatMessage(literalText(msg.toString()))
            } else {
                Main.INSTANCE.server.consoleSender.sendMessage("No valid UUID found in your Syntax.")
            }
        }
    }
}
