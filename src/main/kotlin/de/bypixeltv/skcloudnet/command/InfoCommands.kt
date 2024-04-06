package de.bypixeltv.skcloudnet.command

import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.jorel.commandapi.kotlindsl.stringArgument
import eu.cloudnetservice.driver.inject.InjectionLayer
import eu.cloudnetservice.driver.provider.CloudServiceProvider
import eu.cloudnetservice.driver.provider.ServiceTaskProvider
import eu.cloudnetservice.driver.service.ServiceConfiguration
import eu.cloudnetservice.driver.service.ServiceCreateResult
import eu.cloudnetservice.driver.service.ServiceTask

class InfoCommands {
    private val cnServiceProvider: CloudServiceProvider = InjectionLayer.ext().instance(CloudServiceProvider::class.java)
    private val serviceTaskProvider: ServiceTaskProvider = InjectionLayer.ext().instance(ServiceTaskProvider::class.java)

    val command = commandTree("test") {
        withPermission("skcloudnet.test")
        stringArgument("test") {
            val services = cnServiceProvider.services().map { it.name().split("-")[0] }
            val uniqueServices = LinkedHashSet(services)
            replaceSuggestions(ArgumentSuggestions.stringCollection {
                uniqueServices
            })
            playerExecutor { _, args ->

            }
        }
    }
}