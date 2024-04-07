package de.bypixeltv.skcloudnet.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.ServiceTaskProvider
import eu.cloudnetservice.driver.service.ServiceConfiguration
import eu.cloudnetservice.driver.service.ServiceTask
import eu.cloudnetservice.driver.service.ServiceTask.Builder
import org.bukkit.event.Event

@Name("Set Task MinServiceCount")
@Description("Set the minservicecount of a CloudNet task")
@Examples("set minservicecount of task \"Lobby\" to true")
@Since("1.0")

class EffSetMinServiceCount : Effect() {

    val serviceTaskProvider = InjectionLayer.ext().instance(ServiceTaskProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(EffSetMinServiceCount::class.java, "set (minsercount|msc|minservicecount|sercount) of [cloudnet] task %string% to %number%")
        }
    }

    private var task: Expression<String>? = null
    private var minservicecount: Expression<Number>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.task = expressions[0] as Expression<String>
        this.minservicecount = expressions[1] as Expression<Number>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "set minservicecount of task ${task.toString()} to ${minservicecount.toString()}"
    }

    override fun execute(event: Event?) {
        val task = task?.getSingle(event)
        val minservicecount = minservicecount?.getSingle(event)
        val serviceTask = serviceTaskProvider.serviceTask(task.toString())
        if (serviceTask != null) {
            val minServiceCountInt = when (val count = minservicecount) {
                is Long -> count.toInt()
                else -> count as Int
            }
            val provider = ServiceTask.builder(serviceTask).minServiceCount(minServiceCountInt).build()
            serviceTaskProvider.addServiceTask(provider)
        }
    }
}