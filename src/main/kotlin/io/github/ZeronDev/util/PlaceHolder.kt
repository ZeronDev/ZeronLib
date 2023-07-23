package io.github.ZeronDev.util

import io.github.ZeronDev.LibraryPlugin.plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PlaceHolder(val message: String) {
    fun store() = message
    fun load(player: Player? = null) : String {
        val text = message
        if (player != null) {
            placeHolderListP.keys.forEach { msg ->
                text.replace(msg, placeHolderListP[msg]!!(player))
            }
        }
        placeHolderListS.keys.forEach { msg ->
            text.replace(msg, placeHolderListS[msg]!!(plugin))
        }
        return text
    }

    companion object {
        val placeHolderListP: MutableMap<String, PContent> = mutableMapOf(
            "%name%" to { p -> p.name},
            "%ping%" to { p -> p.ping.toString()}
        )
        val placeHolderListS: MutableMap<String, SContent> = mutableMapOf(
            "%online%" to { p -> Bukkit.getOnlinePlayers().size.toString()},
            "%tps%-1min" to { p -> p.server.tps[0].toInt().toString()},
            "%tps%-5min" to { p -> p.server.tps[1].toInt().toString()},
            "%tps%-15min" to { p -> p.server.tps[2].toInt().toString()},
            "%now%" to {
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"))
            }
        )

        fun newPlayerPH(message: String, content: PContent) {
            placeHolderListP[message] = content
        }
        fun newServerPH(message: String, content: SContent) {
            placeHolderListS[message] = content
        }
    }
}


typealias PContent = (Player) -> (String)
typealias SContent = (Plugin) -> (String)