package io.github.ZeronDev.gui

import io.github.ZeronDev.LibraryPlugin.plugin
import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

class InvHandler(title: String, lines: Int) : InventoryHolder {
    internal var onclick: ((InventoryClickEvent) -> Unit)? = null
    internal var onclose: ((InventoryCloseEvent) -> Unit)? = null
    internal var onopen: ((InventoryOpenEvent) -> Unit)? = null
    private  var spaceSetter: ItemStack? = null
    internal var slotList: List<Int> = listOf()
    internal val slotMap = mutableMapOf<Int, (SlotFunc).()->Unit>()
    private val inv = Bukkit.createInventory(this, lines*9, text(title))

    companion object {
        var isRegistered = false
    }

    init {
        if (!isRegistered) {
            plugin.server.pluginManager.registerEvents(GuiListener, plugin)
            isRegistered = true
        }
    }

    fun slot(slot: Int, func: (SlotFunc).()->Unit) {
        slotMap[slot] = func
        item(slot, SlotFunc().apply(func).item ?: ItemStack(Material.AIR))
    }

    fun item(slot: Int): ItemStack? = inv.getItem(slot)
    fun item(slot: Int, item: ItemStack) = inv.setItem(slot, item)

    fun onOpen(func: (InventoryOpenEvent) -> Unit) {
        this.onopen = func
    }
    fun onClose(func: (InventoryCloseEvent) -> Unit) {
        this.onclose = func
    }
    fun onClick(func: (InventoryClickEvent) -> Unit) {
        this.onclick = func
    }
    fun space(slotList: List<Int>, item: ItemStack) {
        this.spaceSetter = item
        this.slotList = slotList
    }
    fun build() : Inventory {
        if (spaceSetter != null) {
            slotList.forEach { index ->
                inv.setItem(index, spaceSetter)
            }
        }
        return inv
    }

    override fun getInventory(): Inventory = inv
}