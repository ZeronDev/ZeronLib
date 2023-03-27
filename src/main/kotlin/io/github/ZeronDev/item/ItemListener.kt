package io.github.ZeronDev.item

import com.google.gson.Gson
import io.github.ZeronDev.LibraryPlugin.plugin
import io.github.ZeronDev.config.ConfigHandler.deserializeToObject
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType

object ItemListener : Listener {
    fun register() {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }
    @EventHandler
    fun onInteract(e: PlayerInteractEvent) {
        if (e.item?.itemMeta?.persistentDataContainer?.has(NamespacedKey(plugin, "InteractFun")) == true) {
            e.item?.itemMeta?.persistentDataContainer?.get(NamespacedKey(plugin, "InteractFun")
                , PersistentDataType.BYTE_ARRAY)?.deserializeToObject(ItemStackBuilder::class)?.interactFunc?.invoke(e)
        }
    }
    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        if (e.currentItem?.itemMeta?.persistentDataContainer?.has(NamespacedKey(plugin, "cannotDrag")) == true) {
            e.isCancelled
        }
    }
    @EventHandler
    fun onDrag(e: InventoryDragEvent) {
        if (e.cursor?.itemMeta?.persistentDataContainer?.has(NamespacedKey(plugin, "cannotDrag")) == true) {
            e.isCancelled = true
        }
    }
}