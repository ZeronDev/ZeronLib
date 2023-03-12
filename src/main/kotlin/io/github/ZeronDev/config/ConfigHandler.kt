package io.github.ZeronDev.config

import com.google.gson.Gson
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

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
    fun Any.serializeToString() : String {
        return Gson().toJson(this)
    }
    fun <T : Any>String.toObject(type: Class<T>) : T {
        return Gson().fromJson(this, type)
    }
}