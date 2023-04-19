package io.github.ZeronDev.region

import io.github.ZeronDev.LibraryPlugin.plugin
import io.papermc.paper.event.entity.EntityMoveEvent
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class Region(val pointOne: Location, val pointTwo: Location, val containsAllY: Boolean = false) {
    constructor(center: Location, radius: Int, containsAllY: Boolean = false) : this(
        Location(center.world, center.x+radius, center.y+radius,center.z+radius),
        Location(center.world, center.x-radius, center.y-radius,center.z-radius),
        containsAllY)

    fun isEntered(loc: Location) : Boolean {
        val pointOneX: Double = if (pointOne.x > pointTwo.x) pointOne.x else pointTwo.x
        val pointTwoX: Double = if (pointOne.x > pointTwo.x) pointTwo.x else pointOne.x
        val pointOneZ: Double = if (pointOne.z > pointTwo.z) pointOne.z else pointTwo.z
        val pointTwoZ: Double = if (pointOne.z > pointTwo.z) pointTwo.z else pointOne.z
        val pointOneY: Double = if (pointOne.y > pointTwo.y) pointOne.y else pointTwo.y
        val pointTwoY: Double = if (pointOne.y > pointTwo.y) pointTwo.y else pointOne.y

        if (loc.x in pointTwoX..pointOneX) {
            if (loc.z in pointTwoZ..pointOneZ) {
                if (loc.x in pointTwoX..pointOneX) {
                    if (loc.z in pointTwoZ..pointOneZ) {
                        if (containsAllY) return true
                        else if (loc.y in pointTwoY..pointOneY) return true
                    }
                }
            }
        }
        return false
    }

    fun Entity.isInRegion(region: Region) : Boolean {
        return region.isEntered(this.location)
    }

    fun onEnterPlayer(manager: (PlayerMoveEvent) -> Unit) : Region {
        plugin.server.pluginManager.registerEvents(object : Listener {
            @EventHandler
            fun onEnter(e: PlayerMoveEvent) {
                if (e.hasChangedPosition() && isEntered(e.to) && !isEntered(e.from)) {
                    manager(e)
                }
            }
        }, plugin)
        return this
    }
    fun onEnterEntity(manager: (EntityMoveEvent) -> Unit) : Region {
        plugin.server.pluginManager.registerEvents(object : Listener {
            @EventHandler
            fun onEnter(e: EntityMoveEvent) {
                if (e.hasChangedPosition() && isEntered(e.to) && !isEntered(e.from)) {
                    manager(e)
                }
            }
        }, plugin)
        return this
    }

    fun onPlayerLeave(manager: (PlayerMoveEvent) -> Unit) : Region {
        plugin.server.pluginManager.registerEvents(object : Listener {
            @EventHandler
            fun onLeave(e: PlayerMoveEvent) {
                if (e.hasChangedPosition() && isEntered(e.from) && !isEntered(e.to)) {
                    manager(e)
                }
            }
        }, plugin)
        return this
    }
    fun onEntityLeave(manager: (EntityMoveEvent) -> Unit) : Region {
        plugin.server.pluginManager.registerEvents(object : Listener {
            @EventHandler
            fun onLeave(e: EntityMoveEvent) {
                if (e.hasChangedPosition() && isEntered(e.from) && !isEntered(e.to)) {
                    manager(e)
                }
            }
        }, plugin)
        return this
    }


    fun getAllPlayers() : MutableList<Player> {
        return pointOne.world.players.filter { player -> player.isInRegion(this) }.toMutableList()
    }
    fun getAllEntities() : MutableList<Entity> {
        return pointOne.world.entities.filter { entity -> entity.isInRegion(this)}.toMutableList()
    }
}