package de.bypixeltv.skcloudnet.elements.effects

import ch.njol.skript.Skript
import ch.njol.skript.doc.Description
import ch.njol.skript.doc.Examples
import ch.njol.skript.doc.Name
import ch.njol.skript.doc.Since
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import org.bukkit.event.Event

@Name("Stop Service")
@Description("Start a CloudNet service by its name.")
@Examples("start cloudnet service \"Lobby-1\"")
@Since("1.0")

class EffStartService : Effect() {

    private val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerEffect(EffStartService::class.java, "start [cloudnet] service %string%")
        }
    }

    private var serviceExpression: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.serviceExpression = expressions[0] as Expression<String>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "stop cloudnet service ${serviceExpression?.getSingle(event)}"
    }

    override fun execute(event: Event?) {
        val service = serviceExpression?.getSingle(event)
        service?.let {
            cnServiceProvider.serviceProviderByName(it).start()
        }
    }
}