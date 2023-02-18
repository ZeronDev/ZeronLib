package com.github.ZeronDev.listener

import org.bukkit.Bukkit
import org.bukkit.event.Event

class ListenerManager<T : Event>(val event: T) {
    companion object {
        fun callEvent(e: Event) = Bukkit.getPluginManager().callEvent(e)
    }

    fun requires(func: (T)->Boolean) {
        if (!func.invoke(event)) {
            return
        }
    }
    fun requiresOr(func: (T)->Boolean, or: (T)->Unit) {
        if (!func.invoke(event)) {
            or.invoke(event)
            return
        }
    }
}