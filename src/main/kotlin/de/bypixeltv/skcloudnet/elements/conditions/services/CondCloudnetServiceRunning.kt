package de.bypixeltv.skcloudnet.elements.conditions.services

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
import org.bukkit.event.Event


@Name("CloudNet Service Running")
@Description("Returns if a CloudNet service is running or not")
@Examples("if cloudnet service \"Lobby-1\" is running:    send \"Lobby-1 is online\"")
@Since("1.1")

class CondCloudnetServiceRunning : Condition() {

    val cnServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerCondition(CondCloudnetServiceRunning::class.java, "[lifecycle] [of] [cloudnet] service %string% (1¦is|2¦is(n't| not)) (running|started)")
        }
    }

    private var service: Expression<String>? = null

    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.service = expressions[0] as Expression<String>?
        isNegated = parser.mark === 1
        return true
    }

    override fun check(e: Event?): Boolean {
        val service = service?.getSingle(e) ?: return isNegated
        return if (cnServiceProvider.serviceByName(service)?.lifeCycle()?.name == "RUNNING") {
            isNegated
        } else {
            !isNegated
        }
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "${service.toString()} is running"
    }

}