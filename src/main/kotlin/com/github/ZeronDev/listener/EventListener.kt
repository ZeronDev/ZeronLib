package com.github.ZeronDev.listener

import com.github.ZeronDev.LibraryPlugin.plugin
import com.github.ZeronDev.LibraryPlugin.register
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

object EventListener {
    inline fun <reified T : Event>listen(eventPriority: EventPriority = EventPriority.NORMAL, crossinline func: (ListenerManager<T>).() -> Unit) {
        register()
        when (eventPriority) {
            EventPriority.LOWEST -> {
                Bukkit.getPluginManager().registerEvents(object : Listener {
                    @EventHandler(priority = EventPriority.LOWEST)
                    fun listen(e: T) {
                        func(ListenerManager(e))
                    }
                }, plugin!!)
            }
            EventPriority.LOW -> {
                Bukkit.getPluginManager().registerEvents(object : Listener {
                    @EventHandler(priority = EventPriority.LOW)
                    fun listen(e: T) {
                        func(ListenerManager(e))
                    }
                }, plugin!!)
            }
            EventPriority.NORMAL -> {
                Bukkit.getPluginManager().registerEvents(object : Listener {
                    @EventHandler(priority = EventPriority.NORMAL)
                    fun listen(e: T) {
                        func(ListenerManager(e))
                    }
                }, plugin!!)
            }
            EventPriority.HIGH -> {
                Bukkit.getPluginManager().registerEvents(object : Listener {
                    @EventHandler(priority = EventPriority.HIGH)
                    fun listen(e: T) {
                        func(ListenerManager(e))
                    }
                }, plugin!!)
            }
            EventPriority.HIGHEST -> {
                Bukkit.getPluginManager().registerEvents(object : Listener {
                    @EventHandler(priority = EventPriority.HIGHEST)
                    fun listen(e: T) {
                        func(ListenerManager(e))
                    }
                }, plugin!!)
            }
            else -> {
                Bukkit.getPluginManager().registerEvents(object : Listener {
                    @EventHandler(priority = EventPriority.MONITOR)
                    fun listen(e: T) {
                        func(ListenerManager(e))
                    }
                }, plugin!!)
            }
        }
    }
}