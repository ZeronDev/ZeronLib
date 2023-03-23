package io.github.ZeronDev.listener

import io.github.ZeronDev.LibraryPlugin.plugin
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

object EventListener {
    object EventListener {
        inline fun <reified T : Event>listen(
            eventPriority: EventPriority = EventPriority.NORMAL,
            noinline func: (ListenerManager<T>).() -> Unit
        ) : Listener {
            val listener = object : Listener {}
            plugin.server.pluginManager.registerEvent(T::class.java, listener, eventPriority, { _, event ->
                if (event is T) {
                    val listenerManager = ListenerManager(event).apply(func)
                    if (listenerManager.isRequired) {
                        func(listenerManager)
                    }
                    func(ListenerManager(event).apply(func))
                }
            }, plugin)
            return listener
        }
    }
}