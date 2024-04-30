package de.bypixeltv.skcloudnet.elements.effects.players

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
import eu.cloudnetservice.driver.registry.ServiceRegistry
import eu.cloudnetservice.modules.bridge.player.PlayerManager
import net.axay.kspigot.chat.literalText
import org.bukkit.entity.Player
import org.bukkit.event.Event

@Name("Kick Player From Cloud")
@Description("Kicks a player from the CloudNet network.")
@Examples("kick \"byPixelTV\" parsed as player from proxy due to \"Star SkCloudnet now!!!\"")
@Since("1.4")

class EffKickPlayerFromCloud : Effect() {

    private val serviceRegistry: ServiceRegistry = InjectionLayer.ext().instance(ServiceRegistry::class.java)
    private val playerManager: PlayerManager = serviceRegistry.firstProvider(PlayerManager::class.java)

    companion object{
        init {
            Skript.registerEffect(EffKickPlayerFromCloud::class.java, "(kick|disconnect) %player% from (cloud|cloudnet|network|proxy|bungee|velocity|bungeecord) [(due to|because of|with reason|for) %-string%]")
        }
    }

    private var player: Expression<Player>? = null
    private var reason: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.player = expressions[0] as Expression<Player>
        this.reason = expressions[1] as Expression<String>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "kick ${this.player} from cloudnet ${this.reason}"
    }

    override fun execute(event: Event?) {
        val player = this.player?.getSingle(event)
        var reason = this.reason?.getSingle(event)
        if (reason == null) {
            reason = " "
        }
        if (player != null) {
            literalText(reason).let { playerManager.playerExecutor(player.uniqueId).kick(it) }
        }
    }
}
