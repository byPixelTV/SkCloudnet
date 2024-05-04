package de.bypixeltv.skcloudnet.elements.effects.services

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import org.bukkit.event.Event


class EffStartAllServicesOnTask : Effect() {

    private val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(EffStartAllServicesOnTask::class.java, "start all [cloudnet] services on [the] [task] %string%")
        }
    }

    private var taskExpression: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.taskExpression = expressions[0] as Expression<String>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "start all cloudnet services on task ${taskExpression?.getSingle(event)}"
    }

    override fun execute(event: Event?) {
        val task = taskExpression?.getSingle(event)
        for (service in cnServiceProvider.servicesByTask(task.toString())) {
            cnServiceProvider.serviceProviderByName(service.name()).startAsync()
        }
    }
}