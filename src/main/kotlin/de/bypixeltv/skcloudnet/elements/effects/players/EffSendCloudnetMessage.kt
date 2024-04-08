package de.bypixeltv.skcloudnet.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.registry.ServiceRegistry
import eu.cloudnetservice.modules.bridge.player.PlayerManager
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import org.bukkit.event.Event

@Name("Stop Service")
@Description("Start a CloudNet service by its name.")
@Examples("start cloudnet service \"Lobby-1\"")
@Since("1.0")

class EffSendCloudnetMessage : Effect() {
    private val miniMessages = MiniMessage.miniMessage()
    private val serviceRegistry: ServiceRegistry = InjectionLayer.ext().instance(ServiceRegistry::class.java)
    private val playerManager: PlayerManager = serviceRegistry.firstProvider(PlayerManager::class.java)

    companion object{
        init {
            Skript.registerEffect(EffSendCloudnetMessage::class.java, "send cloudnet message %string% to [(the player|player)] %player%")
        }
    }

    private var text: Expression<String>? = null
    private var player: Expression<Player>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.text = expressions[0] as Expression<String>
        this.player = expressions[0] as Expression<Player>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "send cloudnet message ${text?.getSingle(event)} to the player ${player?.getSingle(event)}"
    }

    override fun execute(event: Event?) {
        val player = player?.getSingle(event)
        val text = text?.getSingle(event)
        if (player != null) {
            val serviceInfo = text?.let {
                miniMessages.deserialize(
                    it
                )
            }?.let { playerManager.playerExecutor(player.uniqueId).sendChatMessage(it) }
        }
    }
}