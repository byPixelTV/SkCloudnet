package de.bypixeltv.skcloudnet.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import eu.cloudnetservice.driver.registry.ServiceRegistry
import eu.cloudnetservice.modules.bridge.player.PlayerManager
import org.bukkit.entity.Player
import org.bukkit.event.Event


@Name("All Cloudnet Services On Task")
@Description("Returns all running CloudNet services running a specify task")
@Examples("loop all cloudnet services on task \"Lobby\":\n" + "\tsend \"%loop-value%\"")
@Since("1.0")

class CondIsPlayerOnNetwork : Condition() {

    private val serviceRegistry: ServiceRegistry = InjectionLayer.ext().instance(ServiceRegistry::class.java)
    private val playerManager: PlayerManager = serviceRegistry.firstProvider(PlayerManager::class.java)

    companion object{
        init {
            Skript.registerCondition(CondIsPlayerOnNetwork::class.java, "%string% (1¦is|2¦is(n't| not)) on network")
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
        val player = this.player?.getSingle(e)
        if (player != null) {
            return !isNegated
        } else {
            return isNegated
        }
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "${player.toString()} is online"
    }

}