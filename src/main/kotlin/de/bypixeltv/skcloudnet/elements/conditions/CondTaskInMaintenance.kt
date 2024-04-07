package de.bypixeltv.skcloudnet.elements.expressions

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import eu.cloudnetservice.driver.provider.ServiceTaskProvider
import org.bukkit.event.Event


@Name("Task in Maintenance")
@Description("Returns if a CloudNet task is in maintenance or not")
@Examples("if task \"Lobby\" is in maintenance:    send \"Lobby is in maintenance\"")
@Since("1.1")

class CondTaskInMaintenance : Condition() {

    val serviceTaskProvider = InjectionLayer.ext().instance(ServiceTaskProvider::class.java)

    companion object{
        init {
            Skript.registerCondition(CondTaskInMaintenance::class.java, "[cloudnet] service %string% (1¦is|2¦is(n't| not)) in maintenance")
        }
    }

    private var task: Expression<String>? = null

    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.task = expressions[0] as Expression<String>?
        isNegated = parser.mark === 1
        return true
    }
    override fun check(e: Event?): Boolean {
        val task = task?.getSingle(e)
        val serviceTask = serviceTaskProvider.serviceTask(task.toString())
        if (serviceTask == null) return isNegated
        return if (serviceTask.maintenance() == false) isNegated else !isNegated
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "${task.toString()} is in maintenance"
    }

}