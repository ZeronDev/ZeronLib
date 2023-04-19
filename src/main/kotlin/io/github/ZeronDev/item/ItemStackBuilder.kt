package io.github.ZeronDev.item

import com.google.gson.Gson
import io.github.ZeronDev.LibraryPlugin
import io.github.ZeronDev.config.ConfigHandler.serializeToByteArray
import io.github.ZeronDev.item.ItemListener.register
import net.kyori.adventure.text.Component.text
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.io.Serializable

class ItemStackBuilder(val material: Material) : Serializable {
    init {
        register()
    }

    @Transient val item: ItemStack = ItemStack(material)
    lateinit var interactFunc: (PlayerInteractEvent) -> Unit

    fun displayName(name: String) : ItemStackBuilder {
        item.itemMeta = item.itemMeta.apply {
            this.displayName(text(name))
        }
        return this
    }
    fun lore(list: MutableList<String>) : ItemStackBuilder {
        item.itemMeta = item.itemMeta.apply {
            this.lore(list.map { text(it) })
        }
        return this
    }
    fun amount(amount: Int) : ItemStackBuilder {
        item.amount = amount
        return this
    }
    fun modelData(data: Int) : ItemStackBuilder {
        item.itemMeta = item.itemMeta.apply {
            this.setCustomModelData(data)
        }
        return this
    }
    fun <T, Z : Any>data(key: NamespacedKey, type: PersistentDataType<T, Z>, value: Z) : ItemStackBuilder {
        item.itemMeta = item.itemMeta.apply {
            this.persistentDataContainer.set(key, type, value)
        }
        return this
    }

    fun build() : ItemStack = item

    fun cannotDrag() : ItemStackBuilder {
        item.itemMeta = item.itemMeta.apply {
            this.persistentDataContainer.set(NamespacedKey(LibraryPlugin.plugin, "cannotDrag"), PersistentDataType.STRING, "TRUE")
        }
        return this
    }
    fun onInteract(func: (PlayerInteractEvent) -> Unit) : ItemStackBuilder {
        interactFunc = func
        item.itemMeta = item.itemMeta?.apply {
            this.persistentDataContainer.set(NamespacedKey(LibraryPlugin.plugin, "InteractFun"), PersistentDataType.BYTE_ARRAY, serializeToByteArray()!!)
        }
        return this
    }
}