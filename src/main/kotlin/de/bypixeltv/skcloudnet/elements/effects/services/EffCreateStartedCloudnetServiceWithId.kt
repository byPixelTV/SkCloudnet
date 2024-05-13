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


class EffCreateStartedCloudnetServiceWithId : Effect() {

    private val serviceTaskProvider: ServiceTaskProvider = InjectionLayer.ext().instance(ServiceTaskProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(EffCreateStartedCloudnetServiceWithId::class.java, "create [a] started [cloudnet] service by [(the task|task|cloudnet task|the cloudnet task)] %string% with (the id|id) %number%")
        }
    }

    private var taskExpression: Expression<String>? = null
    private var idExpression: Expression<Number>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.taskExpression = expressions[0] as Expression<String>
        this.idExpression = expressions[1] as Expression<Number>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "create started cloudnet service by task ${taskExpression.toString()} with id ${idExpression.toString()}"
    }

    override fun execute(event: Event?) {
        val taskExpr = taskExpression?.getSingle(event)
        val idExpr = idExpression?.getSingle(event) ?: -1
        val serviceTask = serviceTaskProvider.serviceTask(taskExpr.toString())
        val serviceInfoSnapshot = serviceTask?.let { ServiceConfiguration.builder(it).taskId(idExpr.toInt()).build().createNewService() }
        serviceInfoSnapshot?.serviceInfo()?.provider()?.startAsync()
    }
}