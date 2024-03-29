package io.github.ZeronDev

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.PluginClassLoader

object LibraryPlugin {
    private val _plugin: JavaPlugin by lazy {

        val loaderField = PluginClassLoader::class.java
            .getDeclaredField("libraryLoader")
            .apply { isAccessible = true }
        val out = Bukkit.getPluginManager().plugins
            .first {
                val classLoader = it.javaClass.classLoader as? PluginClassLoader
                classLoader ?: return@first false
                loaderField[classLoader] == LibraryPlugin::class.java.classLoader
            } as JavaPlugin
        out
    }
    val plugin: JavaPlugin get() = _plugin
}