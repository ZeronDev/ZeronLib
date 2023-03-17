package io.github.ZeronDev.particle

import com.github.shynixn.mccoroutine.bukkit.launch
import io.github.ZeronDev.LibraryPlugin.plugin
import kotlinx.coroutines.delay
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.util.Vector
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object ShapeDrawer {
    fun drawLine(particle: Particle, loc1: Location, loc2: Location, interval: Double = 0.1) {
        val direction = loc1.toVector().subtract(loc2.toVector())

        var count = 0.toDouble()
        while (count <= loc1.distance(loc2)) {
            loc1.world.spawnParticle(particle, loc1.clone().add(Vector().copy(direction).multiply(count)), 1)

            count += interval
        }
    }
    fun drawCircle(particle: Particle, location: Location, radius: Double, interval: Double = 0.1, speed: Int = 0, isVertical: Boolean = false) {
        var angle = 0.toDouble()

        if (isVertical) {
            plugin.launch {
                while (angle <= PI * 2) {
                    val offset = location.direction.clone().multiply(cos(angle) * radius)
                    offset.y = sin(angle) * radius
                    location.add(offset)

                    location.world.spawnParticle(particle, location, 1)
                    location.subtract(offset)

                    angle += interval

                    delay(speed.toLong())
                }
            }
        } else {
            while (angle <= PI * 2) {

                val x = radius * sin(angle)
                val z = radius * cos(angle)

                location.world.spawnParticle(particle, Location(location.world, location.x+x, location.y, location.z+z), 1)

                angle += interval
            }
        }
    }
}