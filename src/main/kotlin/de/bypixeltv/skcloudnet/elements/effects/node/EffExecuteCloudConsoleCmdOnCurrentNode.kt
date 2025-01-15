package de.bypixeltv.skcloudnet.elements.effects.node

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.ClusterNodeProvider
import org.bukkit.event.Event


class EffExecuteCloudConsoleCmdOnCurrentNode : Effect() {

    private val cnNodeProvider: ClusterNodeProvider = InjectionLayer.ext().instance(ClusterNodeProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(EffExecuteCloudConsoleCmdOnCurrentNode::class.java, "execute cloudnet console command %string% [(on current node|on this node|on this cluster node)]")
        }
    }

    private var commandExpression: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.commandExpression = expressions[0] as Expression<String>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "execute cloudnet console command ${commandExpression?.getSingle(event)} on current node"
    }

    override fun execute(event: Event?) {
        val command = commandExpression?.getSingle(event)
        cnNodeProvider.sendCommandLineAsync(command.toString())
    }
}