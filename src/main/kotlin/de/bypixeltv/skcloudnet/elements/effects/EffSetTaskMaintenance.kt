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

@Name("Set Task Maintenance")
@Description("Set the maintenance state of a CloudNet task")
@Examples("set maintenance of task \"Lobby\" to true")
@Since("1.0")

class EffSetTaskMaintenance : Effect() {

    val serviceTaskProvider = InjectionLayer.ext().instance(ServiceTaskProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(EffSetTaskMaintenance::class.java, "set maintenance of [cloudnet] task %string% to %boolean%")
        }
    }

    private var task: Expression<String>? = null
    private var maintenance: Expression<Boolean>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.task = expressions[0] as Expression<String>
        this.maintenance = expressions[1] as Expression<Boolean>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "set maintenance of task ${task.toString()} to ${maintenance.toString()}"
    }

    override fun execute(event: Event?) {
        val task = task?.getSingle(event)
        val maintenance = maintenance?.getSingle(event)
        val serviceTask = serviceTaskProvider.serviceTask(task.toString())
        if (serviceTask != null) {
            val provider = maintenance?.let { ServiceTask.builder(serviceTask).maintenance(it).build() }
            if (provider != null) {
                serviceTaskProvider.addServiceTask(provider)
            }
        }
    }
}