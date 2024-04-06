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
import org.bukkit.event.Event

@Name("Create started service by Task")
@Description("Create a started CloudNet service by a task")
@Examples("create start cloudnet service by task \"Lobby\"")
@Since("1.0")

class EffCreateStartedCloudnetService : Effect() {

    private val serviceTaskProvider: ServiceTaskProvider = InjectionLayer.ext().instance(ServiceTaskProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(EffCreateStartedCloudnetService::class.java, "create [a] started [cloudnet] service by [the] [task] %string%")
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
        return "create started cloudnet service by task ${taskExpression.toString()}"
    }

    override fun execute(event: Event?) {
        val taskExpr = taskExpression?.getSingle(event)
        val serviceTask = serviceTaskProvider.serviceTask(taskExpr.toString())
        val serviceInfoSnapshot = serviceTask?.let { ServiceConfiguration.builder(it).build().createNewService() }
        serviceInfoSnapshot?.serviceInfo()?.provider()?.start()
    }
}