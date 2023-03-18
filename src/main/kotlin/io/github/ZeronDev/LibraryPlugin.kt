package io.github.ZeronDev

import org.bukkit.plugin.java.JavaPlugin

object LibraryPlugin {
    lateinit var plugin: JavaPlugin

    fun init(J_Plugin: JavaPlugin) {
        plugin = J_Plugin
    }
}