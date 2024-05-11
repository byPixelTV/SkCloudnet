package de.bypixeltv.skcloudnet.elements.effects.players

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import de.bypixeltv.skcloudnet.Main
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.registry.ServiceRegistry
import eu.cloudnetservice.modules.bridge.player.PlayerManager
import net.axay.kspigot.chat.literalText
import org.bukkit.event.Event
import org.jetbrains.annotations.Nullable
import java.util.*

@Suppress("unused")
class EffKickPlayerFromCloud : Effect() {

    private val serviceRegistry: ServiceRegistry = InjectionLayer.ext().instance(ServiceRegistry::class.java)
    private val playerManager: PlayerManager = serviceRegistry.firstProvider(PlayerManager::class.java)

    companion object{
        init {
            Skript.registerEffect(EffKickPlayerFromCloud::class.java, "kick %strings% from (cloud|cloudnet|network|proxy|bungee|velocity|bungeecord) [(by reason of|because [of]|on account of|due to|with reason) %-string%]")
        }
    }

    private var uuids: Expression<String>? = null
    @Nullable
    private var reason: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        this.uuids = expressions[0] as Expression<String>
        this.reason = expressions[1] as Expression<String>
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "kick ${this.uuids} from cloudnet ${this.reason}"
    }

    override fun execute(e: Event?) {
        val r = reason?.getSingle(e) ?: ""
        for (p in uuids!!.getArray(e)) {
            val uuidRegex = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"
            val uuidMatchResult = uuidRegex.toRegex().find(p.toString())

            if (uuidMatchResult != null) {
                val uuidString = uuidMatchResult.value
                val uuid = UUID.fromString(uuidString)
                playerManager.playerExecutor(uuid).kick(literalText(r))
            } else {
                Main.INSTANCE.server.consoleSender.sendMessage("No valid UUID found in your Syntax.")
            }
        }
    }
}