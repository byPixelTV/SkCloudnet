package de.bypixeltv.skcloudnet.elements.effects.services

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import org.bukkit.event.Event


class EffExecuteCommandOnService : Effect() {

    private val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(EffExecuteCommandOnService::class.java, "execute command %string% on [cloudnet] service %string%")
        }
    }

    private var commandExpression: Expression<String>? = null
    private var serviceExpression: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.commandExpression = expressions[0] as Expression<String>
        this.serviceExpression = expressions[1] as Expression<String>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "execute cloudnet command ${commandExpression?.getSingle(event)} on service ${serviceExpression?.getSingle(event)}"
    }

    override fun execute(event: Event?) {
        val service = serviceExpression?.getSingle(event)
        val command = commandExpression?.getSingle(event)
        service?.let {
            cnServiceProvider.serviceProviderByName(it).runCommandAsync(command.toString())
        }
    }
}