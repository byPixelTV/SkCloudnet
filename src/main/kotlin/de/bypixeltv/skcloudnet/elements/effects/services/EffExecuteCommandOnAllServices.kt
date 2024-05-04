package de.bypixeltv.skcloudnet.elements.effects.services

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import org.bukkit.event.Event


class EffExecuteCommandOnAllServices : Effect() {

    private val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(EffExecuteCommandOnAllServices::class.java, "execute [cloudnet] command %string% on all services")
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
        return "execute cloudnet command ${commandExpression?.getSingle(event)} on all services"
    }

    override fun execute(event: Event?) {
        val command = commandExpression?.getSingle(event)
        for (service in cnServiceProvider.services()) {
            cnServiceProvider.serviceProviderByName(service.name()).runCommandAsync(command.toString())
        }
    }
}