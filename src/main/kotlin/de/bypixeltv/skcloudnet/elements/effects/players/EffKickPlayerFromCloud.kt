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
import org.jetbrains.annotations.Nullable


@Name("Kick Player From Cloud")
@Description("Kicks a player from the CloudNet network.")
@Examples("kick \"byPixelTV\" parsed as player from proxy due to \"Star SkCloudnet now!!!\"")
@Since("1.4")

class EffKickPlayerFromCloud : Effect() {

    private val serviceRegistry: ServiceRegistry = InjectionLayer.ext().instance(ServiceRegistry::class.java)
    private val playerManager: PlayerManager = serviceRegistry.firstProvider(PlayerManager::class.java)

    companion object{
        init {
            Skript.registerEffect(EffKickPlayerFromCloud::class.java, "kick %players% from (cloud|cloudnet|network|proxy|bungee|velocity|bungeecord) [(by reason of|because [of]|on account of|due to|with reason) %-string%]")
        }
    }

    private var players: Expression<Player>? = null
    @Nullable
    private var reason: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.players = expressions[0] as Expression<Player>
        if (expressions.size > 1 && expressions[1] != null) {
            this.reason = expressions[1] as Expression<String>
        }
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "kick ${this.players} from cloudnet ${this.reason}"
    }

    override fun execute(e: Event?) {
        val r = if (reason != null) reason!!.getSingle(e) else ""
        if (r == null) return
        for (p in players!!.getArray(e)) {
            playerManager.playerExecutor(p.uniqueId).kick(literalText(r))
        }
    }
}