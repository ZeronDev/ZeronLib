package com.github.ZeronDev.coroutine

import com.github.ZeronDev.LibraryPlugin.plugin
import com.github.ZeronDev.LibraryPlugin.register
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask

class RepeatingScheduler {
    var scheduler: BukkitTask? = null
    init {
        register()
    }
    companion object {
        fun scheduleWith(period: Int, func: ()->Unit) : RepeatingScheduler {
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