package com.github.ZeronDev.item

import com.google.gson.Gson
import com.github.ZeronDev.LibraryPlugin
import com.github.ZeronDev.LibraryPlugin.plugin
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

object ItemListener : Listener {
    fun register() {
        LibraryPlugin.register()
        plugin!!.server.pluginManager.registerEvents(this, plugin!!)
    }
    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        if (e.item?.itemMeta?.persistentDataContainer?.has(NamespacedKey.fromString("InteractFun")!!) == true) {
            Gson().fromJson(e.item?.itemMeta?.persistentDataContainer?.get(NamespacedKey.fromString("InteractFun")!!
                , PersistentDataType.STRING), ItemStackBuilder::class.java).interactFunc.invoke(e)
        }
    }
    @EventHandler
    fun onDrag(e: InventoryDragEvent) {
        if (e.cursor?.itemMeta?.persistentDataContainer?.has(NamespacedKey.fromString("cannotDrag")!!) == true) {
            e.isCancelled = true
        }
    }
}