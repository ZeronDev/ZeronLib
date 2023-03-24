package io.github.ZeronDev.listener

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

class ListenerManager<T : Event>(val event: T) {
    var isRequired: Boolean = true

    companion object {
        fun callEvent(e: Event) = Bukkit.getPluginManager().callEvent(e)
        fun unregisterListener(listener: Listener) = HandlerList.unregisterAll(listener)
    }

    fun requires(func: (T)->Boolean) {
        isRequired = func.invoke(event)
    }
}