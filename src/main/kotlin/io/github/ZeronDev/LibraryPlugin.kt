package io.github.ZeronDev

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.PluginClassLoader

object LibraryPlugin {
    var plugin: JavaPlugin? = null

    fun init() {
        plugin = Bukkit.getPluginManager().plugins.first {
            PluginClassLoader::class.java
                .getDeclaredField("libraryLoader")
                .apply { isAccessible = true }[it.javaClass.classLoader] ==
                    LibraryPlugin::class.java.classLoader
        } as JavaPlugin
    }
}