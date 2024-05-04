package de.bypixeltv.skcloudnet.elements.expressions.players

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import de.bypixeltv.skcloudnet.Main
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.registry.ServiceRegistry
import eu.cloudnetservice.modules.bridge.player.PlayerManager
import org.bukkit.event.Event
import java.util.*


class ExprGetCloudnetPlayerService : SimpleExpression<String>() {

    private val serviceRegistry: ServiceRegistry = InjectionLayer.ext().instance(ServiceRegistry::class.java)
    private val playerManager: PlayerManager = serviceRegistry.firstProvider(PlayerManager::class.java)

    companion object{
        init {
            Skript.registerExpression(
                ExprGetCloudnetPlayerService::class.java, String::class.java,
                ExpressionType.SIMPLE, "cloudnet service of [(the player|player)] %string%")
        }
    }

    private var uuid: Expression<String>? = null

    override fun isSingle(): Boolean {
        return true
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        exprs: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parseResult: SkriptParser.ParseResult?
    ): Boolean {
        this.uuid = exprs[0] as Expression<String>?
        return true
    }

    override fun get(e: Event?): Array<String?> {
        val player = this.uuid?.getSingle(e)
        if (player != null) {
            val uuidRegex = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"
            val uuidMatchResult = uuidRegex.toRegex().find(player.toString())

            if (uuidMatchResult != null) {
                val uuidString = uuidMatchResult.value
                val uuid = UUID.fromString(uuidString)
                val serviceInfo = playerManager.onlinePlayer(uuid)?.connectedService()?.serverName()
                return arrayOf(serviceInfo.toString())
            } else {
                Main.INSTANCE.server.consoleSender.sendMessage("No valid UUID found in your Syntax.")
            }
        }
        return arrayOfNulls(0)
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "cloudnet service of ${uuid?.getSingle(e)}"
    }

}