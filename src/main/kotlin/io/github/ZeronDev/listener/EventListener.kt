package io.github.ZeronDev.listener

import io.github.ZeronDev.LibraryPlugin.plugin
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

object EventListener {
    inline fun <reified T : Event> listen(
        eventPriority: EventPriority = EventPriority.NORMAL,
        noinline func: (T) -> Unit
    ): Listener {
        val listener = object : Listener {}
        plugin.server.pluginManager.registerEvent(T::class.java, listener, eventPriority, { _, event ->
            if (event is T) {
                func.invoke(event)
            }
        }, plugin)
        return listener
    }

}