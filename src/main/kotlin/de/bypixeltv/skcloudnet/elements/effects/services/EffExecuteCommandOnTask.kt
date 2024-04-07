package de.bypixeltv.skcloudnet.elements.effects.services

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
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import org.bukkit.event.Event

@Name("Execute command on task")
@Description("Execute a command on all CloudNet services of a cloudnet task.")
@Examples("execute cloudnet command \"say Hi\" on task \"Lobby-1\"")
@Since("1.0")

class EffExecuteCommandOnTask : Effect() {

    private val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(RestartAllServicesOnTask::class.java, "execute [cloudnet] command %string% on [the] [task] %string%")
        }
    }

    private var commandExpression: Expression<String>? = null
    private var taskExpression: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.commandExpression = expressions[0] as Expression<String>
        this.taskExpression = expressions[1] as Expression<String>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "execute cloudnet command ${commandExpression?.getSingle(event)} on task ${taskExpression?.getSingle(event)}"
    }

    override fun execute(event: Event?) {
        val task = taskExpression?.getSingle(event)
        val command = commandExpression?.getSingle(event)
        for (service in cnServiceProvider.servicesByTask(task.toString())) {
            cnServiceProvider.serviceProviderByName(service.name()).runCommandAsync(command.toString())
        }
    }
}