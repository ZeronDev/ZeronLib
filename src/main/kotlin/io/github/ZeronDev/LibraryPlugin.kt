package io.github.ZeronDev

import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.PluginClassLoader

object LibraryPlugin {
    var plugin: Plugin? = null

    init {
        register()
    }

    fun register() {
        if (plugin == null) {
            val plugin = (LibraryPlugin::class.java.classLoader as PluginClassLoader).plugin
            LibraryPlugin.plugin = plugin
        } //모든 리스너 겸용
    }
}