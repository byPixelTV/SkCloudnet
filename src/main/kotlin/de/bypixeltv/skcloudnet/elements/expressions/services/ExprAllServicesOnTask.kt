package de.bypixeltv.skcloudnet.elements.expressions.services

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import org.bukkit.event.Event


@Name("All Cloudnet Services On Task")
@Description("Returns all running CloudNet services running a specify task")
@Examples("loop all cloudnet services on task \"Lobby\":\n" + "\tsend \"%loop-value%\"")
@Since("1.0")

class ExprAllServicesOnTask : SimpleExpression<String>() {

    private val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerExpression(
                ExprAllServicesOnTask::class.java, String::class.java,
                ExpressionType.SIMPLE, "[(all [[of] the]|the)] [running] cloudnet services on [the] [task] %string%")
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
        return "all cloudnet services on task %string%"
    }

}