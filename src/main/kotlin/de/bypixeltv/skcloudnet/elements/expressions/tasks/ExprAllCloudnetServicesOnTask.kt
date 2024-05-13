package de.bypixeltv.skcloudnet.elements.expressions.tasks

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import org.bukkit.event.Event


class ExprAllCloudnetServicesOnTask : SimpleExpression<String>() {

    private val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerExpression(
                ExprAllCloudnetServicesOnTask::class.java, String::class.java,
                ExpressionType.SIMPLE, "[(all [[of] the]|the)] cloudnet services on [(the task|task|cloudnet task|the cloudnet task)] %string%")
        }
    }

    private var task: Expression<String>? = null

    override fun isSingle(): Boolean {
        return false
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
    override fun get(e: Event?): Array<String>? {
        val task = this.task?.getSingle(e)
        if (task != null) {
            return cnServiceProvider.servicesByTask(task).map { it.name() }.toTypedArray()
        }
        return null
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "all cloudnet services on task ${this.task?.getSingle(e)}"
    }

}