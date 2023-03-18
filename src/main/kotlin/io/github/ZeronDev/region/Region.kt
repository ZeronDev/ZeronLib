package io.github.ZeronDev.region

import io.github.ZeronDev.LibraryPlugin.plugin
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class Region(val pointOne: Location, val pointTwo: Location, val containsAllY: Boolean = false) {
    fun isEntered(loc: Location) : Boolean {
        val pointOneX: Double = if (pointOne.x > pointTwo.x) pointOne.x else pointTwo.x
        val pointTwoX: Double = if (pointOne.x > pointTwo.x) pointOne.x else pointTwo.x
        val pointOneZ: Double = if (pointOne.z > pointTwo.z) pointOne.z else pointTwo.z
        val pointTwoZ: Double = if (pointOne.z > pointTwo.z) pointOne.z else pointTwo.z
        val pointOneY: Double = if (pointOne.y > pointTwo.y) pointOne.y else pointTwo.y
        val pointTwoY: Double = if (pointOne.y > pointTwo.y) pointOne.y else pointTwo.y

        if (loc.x in pointOneX..pointTwoX) {
            if (loc.z in pointOneZ..pointTwoZ) {
                if (!containsAllY && loc.y in pointOneY..pointTwoY) {
                    return true
                } else {
                    return true
                }
            }
        }
        return false
    }

    fun onEnter(manager: (PlayerMoveEvent) -> Unit) {
        plugin.server.pluginManager.registerEvents(object : Listener {
            @EventHandler
            fun onEnter(e: PlayerMoveEvent) {
                if (e.hasChangedPosition() && isEntered(e.player.location)) {
                    manager(e)
                }
            }
        }, plugin)
    }

    fun onLeave(manager: (PlayerMoveEvent) -> Unit) {
        plugin.server.pluginManager.registerEvents(object : Listener {
            @EventHandler
            fun onEnter(e: PlayerMoveEvent) {
                if (e.hasChangedPosition() && isEntered(e.from) && !isEntered(e.to)) {
                    manager(e)
                }
            }
        }, plugin)
    }
}