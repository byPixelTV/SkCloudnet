package de.bypixeltv.skcloudnet.elements.effects.services

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import org.bukkit.event.Event


class EffDeleteService : Effect() {

    private val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(EffDeleteService::class.java, "delete [cloudnet] service %string%")
        }
    }

    private var serviceExpression: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.serviceExpression = expressions[0] as Expression<String>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "delete cloudnet service ${serviceExpression?.getSingle(event)}"
    }

    override fun execute(event: Event?) {
        val service = serviceExpression?.getSingle(event)
        service?.let {
            cnServiceProvider.serviceProviderByName(it).deleteAsync()
        }
    }
}