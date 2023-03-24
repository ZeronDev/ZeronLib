package io.github.ZeronDev.listener

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

object EventManager {
    fun callEvent(e: Event) = Bukkit.getPluginManager().callEvent(e)
    fun unregisterListener(listener: Listener) = HandlerList.unregisterAll(listener)
}