package de.bypixeltv.skcloudnet.elements.conditions.tasks

import ch.njol.skript.Skript
import ch.njol.skript.lang.Condition
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.ServiceTaskProvider
import org.bukkit.event.Event


class CondTaskInMaintenance : Condition() {

    val serviceTaskProvider = InjectionLayer.ext().instance(ServiceTaskProvider::class.java)

    companion object{
        init {
            Skript.registerCondition(CondTaskInMaintenance::class.java, "[(the task|task|cloudnet task|the cloudnet task)] %string% (1¦is|2¦is(n't| not)) in maintenance")
        }
    }

    private var task: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.task = expressions[0] as Expression<String>?
        isNegated = parser.mark == 1
        return true
    }
    override fun check(e: Event?): Boolean {
        val task = task?.getSingle(e)
        val serviceTask = serviceTaskProvider.serviceTask(task.toString())
        if (serviceTask == null) {
            return isNegated
        } else {
            if (serviceTask.maintenance() == true) {
                return isNegated
            } else {
                return !isNegated
            }
        }
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "${task.toString()} is in maintenance"
    }

}