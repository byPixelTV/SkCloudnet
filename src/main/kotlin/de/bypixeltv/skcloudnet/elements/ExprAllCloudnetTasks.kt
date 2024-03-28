package de.bypixeltv.skcloudnet.elements

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


@Name("All Running Services")
@Description("Returns all CloudNet tasks")
@Examples("loop all cloudnet tasks:\n" + "\tsend \"%loop-value%\"")
@Since("1.0")

class ExprAllCloudnetTasks : SimpleExpression<String>() {

    val cnServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerExpression(
                ExprAllCloudnetTasks::class.java, String::class.java,
                ExpressionType.SIMPLE, "[(all [[of] the]|the)] cloudnet tasks")
        }
    }

    override fun isSingle(): Boolean {
        return false
    }

    override fun init(
        exprs: Array<Expression<*>?>?,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parseResult: SkriptParser.ParseResult?
    ): Boolean {
        return true
    }

    override fun get(e: Event?): Array<String?> {
        val services = cnServiceProvider.services().map { it.name().split("-")[0] }
        val uniqueServices = LinkedHashSet(services)
        return uniqueServices.toTypedArray()
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "all cloudnet services"
    }

}