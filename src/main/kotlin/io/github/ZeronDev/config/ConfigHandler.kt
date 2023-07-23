package io.github.ZeronDev.config

import com.google.gson.Gson
import io.github.ZeronDev.LibraryPlugin.plugin
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.*
import kotlin.reflect.KClass

class ConfigHandler(val file: File) {
    val config: FileConfiguration = YamlConfiguration.loadConfiguration(file)

    companion object {
        fun Serializable.serializeToByteArray(): ByteArray? {
            try {
                val obj = this
                val bos = ByteArrayOutputStream()
                val out = ObjectOutputStream(bos)
                out.writeObject(obj)
                out.flush()
                return bos.toByteArray()
            } catch (e: Exception) {
                plugin.logger.info("serialize Error")
                e.printStackTrace()
            }
            return null
        }

        inline fun <reified T : Serializable>ByteArray.deserializeToObject(type: KClass<T>): T? {
            try {
                val bytes = this
                val bis = ByteArrayInputStream(bytes)
                val `in` = ObjectInputStream(bis)

                return `in`.readObject() as? T
            } catch (e: Exception) {
                plugin.logger.info("deserialize Error")
                e.printStackTrace()
            }
            return null
        }
        fun deleteConfigFile(file: File) {
            try {
                file.delete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun newConfigFile() : YamlConfiguration? {
        try {
            if (!plugin.dataFolder.exists()) {
                plugin.dataFolder.mkdirs()
            }
            if (!file.exists()) file.createNewFile()
            val config = YamlConfiguration.loadConfiguration(file)
            config.save(file)
            return config
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun saveConfigFile() {
        try {
            plugin.logger.info(config.toString())
            config.save(file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}