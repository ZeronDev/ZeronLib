package io.github.ZeronDev.coroutine

import io.github.ZeronDev.LibraryPlugin.init
import io.github.ZeronDev.LibraryPlugin.plugin
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask

class RepeatingScheduler {
    var scheduler: BukkitTask? = null
    companion object {
        fun scheduleWith(period: Int, func: ()->Unit) : RepeatingScheduler {
            plugin ?: init()
            val sch = RepeatingScheduler()
            sch.apply {
                scheduler = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin!!,
                Runnable { func() }, 0L, period.toLong())
            }
            return sch
        }
    }
    fun stop() {
        scheduler?.cancel()
        scheduler = null
    }
}