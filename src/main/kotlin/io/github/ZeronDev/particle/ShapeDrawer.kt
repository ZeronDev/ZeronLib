package io.github.ZeronDev.particle

import net.kyori.adventure.text.Component.space
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Particle.DUST_COLOR_TRANSITION
import org.bukkit.Particle.DustOptions
import org.bukkit.util.Vector
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


object ShapeDrawer {
    fun drawLine(particle: Particle, loc1: Location, loc2: Location, speed: Double = 0.1, interval: Double = 0.1) {
        val direction = loc1.toVector().subtract(loc2.toVector())

        var count = 0.toDouble()

        val distance = loc1.distance(loc2)

        val vector = loc2.clone().subtract(loc1).toVector().normalize().multiply(interval)

        while (count <= distance) {
            loc1.add(vector)

            loc1.world.spawnParticle(particle, loc1, 1, 0.0, 0.0, 0.0, speed)

            count += interval
        }
    }

    fun drawDustLine(loc1: Location, loc2: Location, speed: Double = 0.1, interval: Double = 0.1, rgb: DustRGB) {
        val direction = loc1.toVector().subtract(loc2.toVector())

        var count = 0.toDouble()

        val distance = loc1.distance(loc2)

        val vector = loc2.clone().subtract(loc1).toVector().normalize().multiply(interval)

        while (count <= distance) {
            loc1.add(vector)

            loc1.world.spawnParticle(
                DUST_COLOR_TRANSITION, loc1.x, loc1.y, loc1.z, 1, 0.0, 0.0, 0.0,
                speed, DustOptions(Color.fromRGB(rgb.red, rgb.green, rgb.blue), rgb.size))

            count += interval
        }
    }

    fun drawCircle(particle: Particle, location: Location, radius: Double, speed: Double = 0.1, interval: Double = 0.1) {
        var angle = 0.toDouble()

//        if (isVertical) {
//            plugin.launch {
//                while (angle <= PI * 2) {
//                    val offset = location.direction.clone().multiply(cos(angle) * radius)
//                    offset.y = sin(angle) * radius
//                    location.add(offset)
//
//                    location.world.spawnParticle(particle, location, 1)
//                    location.subtract(offset)
//
//                    angle += interval
//
//                    delay(speed.toLong())
//                }
//            }
//        } else {
        while (angle <= PI * 2) {

            val x = radius * sin(angle)
            val z = radius * cos(angle)

            location.world.spawnParticle(
                particle,
                Location(location.world, location.x + x, location.y, location.z + z),
                1,
                0.0,
                0.0,
                0.0,
                speed
            )

            angle += interval
        }
//        }
    }
    fun drawDustCircle(location: Location, radius: Double, speed: Double = 0.1, interval: Double = 0.1, rgb: DustRGB) {
        var angle = 0.toDouble()

        while (angle <= PI * 2) {

            val x = radius * sin(angle)
            val z = radius * cos(angle)

            location.world.spawnParticle(
                Particle.DUST_COLOR_TRANSITION,
                location.x + x,
                location.y,
                location.z + z,
                1,
                0.0,
                0.0,
                0.0,
                speed,
                DustOptions(Color.fromRGB(rgb.red, rgb.green, rgb.blue), rgb.size)
            )

            angle += interval
        }
    }
}