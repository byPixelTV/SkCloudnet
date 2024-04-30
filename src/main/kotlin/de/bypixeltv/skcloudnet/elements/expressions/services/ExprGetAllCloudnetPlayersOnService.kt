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
import eu.cloudnetservice.driver.service.ServiceInfoSnapshot
import eu.cloudnetservice.modules.bridge.BridgeDocProperties
import eu.cloudnetservice.modules.bridge.BridgeServiceHelper
import eu.cloudnetservice.modules.bridge.player.ServicePlayer
import org.bukkit.event.Event
import org.jetbrains.annotations.UnknownNullability


@Name("All CloudNet players on service.")
@Description("Returns all CloudNet players on a service.")
@Examples("broadcast all cloudnet players on service \"Lobby\"")
@Since("1.0")

class ExprGetAllCloudnetPlayersOnService : SimpleExpression<String>() {

    private val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)

    companion object{
        init {
            Skript.registerExpression(
                ExprGetAllCloudnetPlayersOnService::class.java, String::class.java,
                ExpressionType.SIMPLE, "all [of the] cloudnet players on [the] service %string%")
        }
    }

    override fun isSingle(): Boolean {
        return false
    }

    private var service: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        exprs: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parseResult: SkriptParser.ParseResult?
    ): Boolean {
        this.service = exprs[0] as Expression<String>?
        return true
    }

    override fun get(e: Event?): Array<String?>? {
        val service = this.service?.getSingle(e)
        val servicePlayers = service?.let { cnServiceProvider.serviceByName(it)!!.readProperty(BridgeDocProperties.PLAYERS) }
        return servicePlayers?.map { it.name }?.toTypedArray()
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "all cloudnet players on service ${this.service?.getSingle(e)}"
    }

}