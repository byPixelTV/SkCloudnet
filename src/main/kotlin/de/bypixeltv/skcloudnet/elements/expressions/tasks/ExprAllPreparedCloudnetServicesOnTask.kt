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


class ExprAllPreparedCloudnetServicesOnTask : SimpleExpression<String>() {

    private val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerExpression(
                ExprAllPreparedCloudnetServicesOnTask::class.java, String::class.java,
                ExpressionType.SIMPLE, "[(all [[of] the]|the)] prepared cloudnet services on [(the task|task|cloudnet task|the cloudnet task)] %string%")
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
    override fun get(e: Event?): Array<String?>? {
        val task = this.task?.getSingle(e)
        val preparedServices = mutableListOf<String?>()
        if (task != null) {
            for (service in cnServiceProvider.servicesByTask(task)) {
                if (service.lifeCycle().name == "PREPARED") {
                    preparedServices.add(service.name())
                }
            }
            val preparedServicesArray = preparedServices.toTypedArray()
            return preparedServicesArray
        }
        return null
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "all prepared cloudnet services on task ${this.task?.getSingle(e)}"
    }

}