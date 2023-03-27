package io.github.ZeronDev.config

import com.google.gson.Gson
import io.github.ZeronDev.LibraryPlugin.plugin
import org.bukkit.configuration.file.YamlConfiguration
import java.io.*
import kotlin.reflect.KClass

object ConfigHandler {
    fun newConfigFile(file: File) : YamlConfiguration? {
        try {
            if (!file.exists()) file.createNewFile()
            val config = YamlConfiguration.loadConfiguration(file)
            config.save(file)
            return config
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    fun getConfigFile(file: File) : YamlConfiguration? {
        try {
            return YamlConfiguration.loadConfiguration(file)
        } catch (e: Exception) {
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
    fun saveConfigFile(file: File) {
        try {
            YamlConfiguration.loadConfiguration(file).save(file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
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
}