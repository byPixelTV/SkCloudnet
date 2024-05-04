package de.bypixeltv.skcloudnet.elements.effects.services

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import org.bukkit.event.Event


class RestartAllServices : Effect() {

    private val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(RestartAllServicesOnTask::class.java, "restart all [cloudnet] services")
        }
    }

    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "restart all cloudnet services"
    }

    override fun execute(event: Event?) {
        for (service in cnServiceProvider.services()) {
            cnServiceProvider.serviceProviderByName(service.name()).restartAsync()
        }
    }
}