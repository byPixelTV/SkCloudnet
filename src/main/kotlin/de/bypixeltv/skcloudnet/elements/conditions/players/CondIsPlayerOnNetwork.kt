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
import net.axay.kspigot.chat.literalText
import org.bukkit.entity.Player
import org.bukkit.event.Event
import java.util.*


@Name("Is Player On Network")
@Description("Checks if a player is on the network.")
@Examples("if player is on network:", "    send \"You are on the network!\" to player", "else:", "    send \"You are not on the network!\" to player")
@Since("1.3")

class CondIsPlayerOnNetwork : Condition() {

    private val serviceRegistry: ServiceRegistry = InjectionLayer.ext().instance(ServiceRegistry::class.java)
    private val playerManager: PlayerManager = serviceRegistry.firstProvider(PlayerManager::class.java)

    companion object{
        init {
            Skript.registerCondition(CondIsPlayerOnNetwork::class.java, "%string% (is|is(n't| not)) on (network|cloud|proxy|bungee|velocity|bungeecord|cloudnet)")
        }
    }

    private var uuid: Expression<String>? = null

    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.uuid = expressions[0] as Expression<String>?
        isNegated = parser.mark === 1
        return true
    }

    override fun check(e: Event?): Boolean {
        val uuid = this.uuid?.getSingle(e)
        val uuidRegex = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"
        val uuidMatchResult = uuidRegex.toRegex().find(uuid.toString())

        if (uuidMatchResult != null) {
            val uuidString = uuidMatchResult.value
            val uuid = UUID.fromString(uuidString)
            if (playerManager.onlinePlayer(uuid) != null) {
                // Player is online
                return isNegated
            } else {
                // Player is not online
                return !isNegated
            }
        } else {
            Main.INSTANCE.server.consoleSender.sendMessage("No valid UUID found in your Syntax.")
            return !isNegated
        }
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "${uuid.toString()} is online"
    }

}