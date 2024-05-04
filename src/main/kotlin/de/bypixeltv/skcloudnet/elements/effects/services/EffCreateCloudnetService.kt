package de.bypixeltv.skcloudnet.elements.effects.services

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.ServiceTaskProvider
import eu.cloudnetservice.driver.service.ServiceConfiguration
import org.bukkit.event.Event


class EffCreateCloudnetService : Effect() {

    private val serviceTaskProvider: ServiceTaskProvider = InjectionLayer.ext().instance(ServiceTaskProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(EffCreateCloudnetService::class.java, "create [a] [cloudnet] service by [the] [task] %string%")
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
        return "create cloudnet service by task ${taskExpression.toString()}"
    }

    override fun execute(event: Event?) {
        val taskExpr = taskExpression?.getSingle(event)
        val serviceTask = serviceTaskProvider.serviceTask(taskExpr.toString())
        val serviceInfoSnapshot = serviceTask?.let { ServiceConfiguration.builder(it).build().createNewServiceAsync() }
    }
}