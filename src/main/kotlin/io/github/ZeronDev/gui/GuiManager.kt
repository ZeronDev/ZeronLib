package io.github.ZeronDev.gui

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

object GuiManager {
    fun newInventory(title: String, lines: Int, func: InvHandler.() -> Unit) : Inventory {
        return InvHandler(title, lines).apply(func).build()
    }
    fun Player.openInv(title: String, lines: Int, func: InvHandler.() -> Unit) {
        this.openInventory(newInventory(title, lines, func))
    }
}