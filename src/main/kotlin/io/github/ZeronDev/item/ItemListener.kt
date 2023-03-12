package io.github.ZeronDev.item

import com.google.gson.Gson
import io.github.ZeronDev.LibraryPlugin.init
import io.github.ZeronDev.LibraryPlugin.plugin
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

object ItemListener : Listener {
    fun register() {
        plugin ?: init()
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
    fun onClick(e: InventoryClickEvent) {
        if (e.currentItem?.itemMeta?.persistentDataContainer?.has(NamespacedKey.fromString("cannotDrag")!!) == true) {
            e.isCancelled
        }
    }
    @EventHandler
    fun onDrag(e: InventoryDragEvent) {
        if (e.cursor?.itemMeta?.persistentDataContainer?.has(NamespacedKey.fromString("cannotDrag")!!) == true) {
            e.isCancelled = true
        }
    }
}