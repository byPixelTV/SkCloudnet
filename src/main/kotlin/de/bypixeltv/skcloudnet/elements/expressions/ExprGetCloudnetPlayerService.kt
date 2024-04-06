package de.bypixeltv.skcloudnet.elements.expressions

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
import eu.cloudnetservice.driver.registry.ServiceRegistry
import eu.cloudnetservice.modules.bridge.player.PlayerManager
import org.bukkit.entity.Player
import org.bukkit.event.Event


@Name("CloudNet Service of Player")
@Description("Returns the CloudNet service of a player.")
@Examples("send cloudnet service of \"byPixelTV\" parsed as player")
@Since("1.0")

class ExprGetCloudnetPlayerService : SimpleExpression<String>() {

    private val serviceRegistry: ServiceRegistry = InjectionLayer.ext().instance(ServiceRegistry::class.java)
    private val playerManager: PlayerManager = serviceRegistry.firstProvider(PlayerManager::class.java)

    companion object{
        init {
            Skript.registerExpression(
                ExprGetCloudnetPlayerService::class.java, String::class.java,
                ExpressionType.SIMPLE, "cloudnet service of [the player] %player%")
        }
    }

    private var player: Expression<Player>? = null

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
        this.player = exprs[0] as Expression<Player>?
        return true
    }

    override fun get(e: Event?): Array<String?> {
        val player = this.player?.getSingle(e)
        if (player != null) {
            val serviceInfo = playerManager.onlinePlayer(player.uniqueId)?.connectedService()?.serverName()
            return arrayOf(serviceInfo)
        }
        return arrayOfNulls(0)
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "cloudnet service of ${player?.getSingle(e)}"
    }

}