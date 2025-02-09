package de.bypixeltv.skcloudnet.elements.effects.services

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import eu.cloudnetservice.modules.bridge.BridgeServiceHelper
import org.bukkit.event.Event


class EffSetServiceStateToIngame : Effect() {

    private val bridgeServiceHelper: BridgeServiceHelper = InjectionLayer.ext().instance(BridgeServiceHelper::class.java)

    companion object{
        init {
            Skript.registerEffect(EffSetServiceStateToIngame::class.java, "set state of [this] service to ingame")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun init(
        expressions: Array<Expression<*>>,
        matchedPattern: Int,
        isDelayed: Kleenean,
        parser: SkriptParser.ParseResult
    ): Boolean {
        return true
    }

    override fun toString(event: Event?, debug: Boolean): String {
        return "set state of this service to ingame"
    }

    override fun execute(event: Event?) {
        bridgeServiceHelper.changeToIngame()
    }
}