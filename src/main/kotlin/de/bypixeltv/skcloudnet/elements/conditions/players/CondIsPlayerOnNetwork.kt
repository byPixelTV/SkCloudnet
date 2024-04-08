package de.bypixeltv.skcloudnet.elements.conditions.players

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import de.bypixeltv.skcloudnet.Main
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import eu.cloudnetservice.driver.registry.ServiceRegistry
import eu.cloudnetservice.modules.bridge.player.PlayerManager
import org.bukkit.entity.Player
import org.bukkit.event.Event


@Name("Is Player On Network")
@Description("Checks if a player is on the network.")
@Examples("if player is on network:", "    send \"You are on the network!\" to player", "else:", "    send \"You are not on the network!\" to player")
@Since("1.3")

class CondIsPlayerOnNetwork : Condition() {

    private val serviceRegistry: ServiceRegistry = InjectionLayer.ext().instance(ServiceRegistry::class.java)
    private val playerManager: PlayerManager = serviceRegistry.firstProvider(PlayerManager::class.java)

    companion object{
        init {
            Skript.registerCondition(CondIsPlayerOnNetwork::class.java, "%player% (1¦is|2¦is(n't| not)) on network")
        }
    }

    private var player: Expression<Player>? = null

    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.player = expressions[0] as Expression<Player>?
        isNegated = parser.mark === 1
        return true
    }

    override fun check(e: Event?): Boolean {
        val playerName = this.player?.getSingle(e)?.name
        val onlinePlayerNames = playerManager.onlinePlayers().names()
        Main.INSTANCE.server.consoleSender.sendMessage("onlinePlayerNames: $onlinePlayerNames")

        if (playerName in onlinePlayerNames) {
            // Player is online
            return !isNegated
        } else {
            // Player is not online
            return isNegated
        }
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "${player.toString()} is online"
    }

}