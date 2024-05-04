package de.bypixeltv.skcloudnet.elements.expressions.tasks

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.registry.ServiceRegistry
import eu.cloudnetservice.modules.bridge.player.PlayerManager
import org.bukkit.event.Event


class ExprGetAllCloudnetPlayersOnTask : SimpleExpression<String>() {

    private val serviceRegistry: ServiceRegistry = InjectionLayer.ext().instance(ServiceRegistry::class.java)
    private val playerManager: PlayerManager = serviceRegistry.firstProvider(PlayerManager::class.java)

    companion object{
        init {
            Skript.registerExpression(
                ExprGetAllCloudnetPlayersOnTask::class.java, String::class.java,
                ExpressionType.SIMPLE, "all [of the] cloudnet players on [the] task %string%")
        }
    }

    override fun isSingle(): Boolean {
        return false
    }

    private var task: Expression<String>? = null

    @Suppress("UNCHECKED_CAST")
    override fun init(
        exprs: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean?,
        parseResult: SkriptParser.ParseResult?
    ): Boolean {
        this.task = exprs[0] as Expression<String>?
        return true
    }

    override fun get(e: Event?): Array<String>? {
        val task = this.task?.getSingle(e)
        return task?.let { playerManager.taskOnlinePlayers(it).names().toTypedArray() }
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "all cloudnet players on task ${this.task?.getSingle(e)}"
    }

}