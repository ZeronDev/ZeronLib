package io.github.ZeronDev.util

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.permissions.Permission

object Util {
    fun registerPermission(permission: String) {
        if (Bukkit.getPluginManager().getPermission(permission) == null) {
            Bukkit.getPluginManager().addPermission(Permission(permission))
            Bukkit.reloadPermissions()
        }
    }

    fun Player.isFull(item: ItemStack? = null) : Boolean {
        return if (inventory.firstEmpty() == -1) {
            if (item == null || inventory.all(item).isEmpty()) return true

            val storage = inventory.storageContents

            storage.filter {
                it!!.isSimilar(item)
            }.sumOf { it!!.amount } + item.amount > storage.count {
                it!!.isSimilar(item)
            } * item.maxStackSize
        } else false
    }
}