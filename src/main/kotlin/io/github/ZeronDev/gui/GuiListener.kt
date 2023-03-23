package io.github.ZeronDev.gui

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

object GuiListener : Listener {
    @EventHandler
    fun onOpen(e: InventoryOpenEvent) {
        (e.inventory.holder as? InvHandler)?.let { inv ->
            inv.onopen?.invoke(e)
        }
    }
    @EventHandler
    fun onClose(e: InventoryCloseEvent) {
        (e.inventory.holder as? InvHandler)?.let {
            it.onclose?.invoke(e)
        }
    }
    @EventHandler
    fun onClick(e: InventoryClickEvent) {
        (e.inventory.holder as? InvHandler)?.let {
            if (it.slotMap.keys.contains(e.slot)) {
                it.slotMap[e.slot]!!.invoke(SlotFunc().apply(it.slotMap[e.slot]!!).apply {
                    event = e
                })
                return
            } else if (it.slotList.contains(e.slot)){
                e.isCancelled = true
            }
        }
    }
}