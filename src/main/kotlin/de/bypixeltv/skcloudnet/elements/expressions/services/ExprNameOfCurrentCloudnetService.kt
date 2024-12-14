package de.bypixeltv.skcloudnet.elements.expressions.services

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.wrapper.holder.ServiceInfoHolder
import org.bukkit.event.Event

class ExprNameOfCurrentCloudnetService: SimpleExpression<String>() {
    private val serviceInfoHolder: ServiceInfoHolder = InjectionLayer.ext().instance(ServiceInfoHolder::class.java)

    companion object{
        init {
            Skript.registerExpression(
                ExprAllCloudnetServices::class.java, String::class.java,
                ExpressionType.SIMPLE, "name of [current] (cloudnet|cloud) service")
        }
    }

    override fun isSingle(): Boolean {
        return true
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
        return arrayOf(serviceInfoHolder.serviceInfo().name())
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "name of current cloudnet service"
    }
}