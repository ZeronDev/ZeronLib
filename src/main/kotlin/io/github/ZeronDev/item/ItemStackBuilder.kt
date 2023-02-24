package io.github.ZeronDev.item

import com.google.gson.Gson
import io.github.ZeronDev.item.ItemListener.register
import net.kyori.adventure.text.Component.text
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class ItemStackBuilder(val material: Material) {
    init {
        register()
    }

    val item: ItemStack = ItemStack(material)
    lateinit var interactFunc: (PlayerInteractEvent) -> Unit

    fun displayName(name: String) : ItemStackBuilder {
        item.itemMeta = item.itemMeta.apply {
            displayName(text(name))
        }
        return this
    }
    fun lore(list: MutableList<String>) : ItemStackBuilder {
        item.itemMeta = item.itemMeta.apply {
            lore(list)
        }
        return this
    }
    fun amount(amount: Int) : ItemStackBuilder {
        item.amount = amount
        return this
    }
    fun modelData(data: Int) : ItemStackBuilder {
        item.itemMeta = item.itemMeta.apply {
            setCustomModelData(data)
        }
        return this
    }
    fun <T, Z : Any>data(key: NamespacedKey, type: PersistentDataType<T, Z>, value: Z) : ItemStackBuilder {
        item.itemMeta = item.itemMeta.apply {
            persistentDataContainer.set(key, type, value)
        }
        return this
    }

    fun build() : ItemStack = item

    fun cannotDrag() : ItemStackBuilder {
        item.itemMeta = item.itemMeta.apply {
            persistentDataContainer.set(NamespacedKey.fromString("cannotDrag")!!, PersistentDataType.STRING, "TRUE")
        }
        return this
    }
    fun onInteract(func: (PlayerInteractEvent) -> Unit) : ItemStackBuilder {
        item.itemMeta = item.itemMeta.apply {
            persistentDataContainer.set(NamespacedKey.fromString("InteractFun")!!, PersistentDataType.STRING, Gson().toJson(this))
        }
        interactFunc = func
        return this
    }
}