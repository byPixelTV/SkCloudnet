package de.bypixeltv.skcloudnet.elements.expressions.tasks

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.ServiceTaskProvider
import org.bukkit.event.Event


class ExprMinServiceCount : SimpleExpression<String>() {

    val serviceTaskProvider = InjectionLayer.ext().instance(ServiceTaskProvider::class.java)

    companion object{
        init {
            Skript.registerExpression(
                ExprMinServiceCount::class.java, String::class.java,
                ExpressionType.SIMPLE, "minservicecount of [the] [cloudnet] task %string%")
        }
    }

    private var task: Expression<String>? = null

    override fun isSingle(): Boolean {
        return true
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        exprs: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parseResult: SkriptParser.ParseResult?
    ): Boolean {
        this.task = exprs[0] as Expression<String>?
        return true
    }
    override fun get(e: Event?): Array<out String?> {
        val task = this.task?.getSingle(e)
        if (task != null) {
            val serviceTask = serviceTaskProvider.serviceTask(task.toString())
            if (serviceTask != null) {
                return arrayOf(serviceTask.minServiceCount().toString())
            }
        }
        return arrayOfNulls(0)
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "minservicecount of ${task.toString()}"
    }

}