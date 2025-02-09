package de.bypixeltv.skcloudnet.elements.expressions.services

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.modules.bridge.BridgeServiceHelper
import org.bukkit.event.Event


class ExprGetStateOfCurrentService : SimpleExpression<String>() {

    private val bridgeServiceHelper: BridgeServiceHelper = InjectionLayer.ext().instance(BridgeServiceHelper::class.java)

    companion object{
        init {
            Skript.registerExpression(
                ExprGetStateOfCurrentService::class.java, String::class.java,
                ExpressionType.SIMPLE, "[current] state of [this] [cloudnet|cloud] service")
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
        return arrayOf(bridgeServiceHelper.state().get())
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "current state of this cloudnet service"
    }

}