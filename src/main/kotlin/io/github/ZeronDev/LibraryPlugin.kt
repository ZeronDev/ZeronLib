package io.github.ZeronDev

import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.PluginClassLoader

object LibraryPlugin {
    var plugin: Plugin? = null
    init {
        init()
    }

    fun init() {
        plugin = (LibraryPlugin::class.java.classLoader as PluginClassLoader).plugin
    }
}