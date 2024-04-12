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


@Name("CloudNet Service Prepared")
@Description("Returns if a CloudNet service is prepared or not")
@Examples("if cloudnet service \"Lobby-1\" is prepared:    send \"Lobby-1 is prepared\"")
@Since("1.1")

class CondCloudnetServicePrepared : Condition() {

    val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerCondition(CondCloudnetServicePrepared::class.java, "[lifecycle] [of] [cloudnet] service %string% (1¦is|2¦is(n't| not)) prepared")
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
        if (cnServiceProvider.serviceByName(service)?.lifeCycle()?.name == "PREPARED") {
            return isNegated
        } else {
            return !isNegated
        }
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "${service.toString()} is prepared"
    }

}