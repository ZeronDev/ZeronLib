package io.github.ZeronDev.util

import io.github.ZeronDev.listener.EventListener.listen
import io.papermc.paper.event.player.AsyncChatEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

class ChatChannel(val channelName: String) {
    private val playerList = mutableListOf<UUID>()

    fun addPlayer(player: Player) {
        playerList.add(player.uniqueId)
    }
    fun removePlayer(player: Player) {
        playerList.remove(player.uniqueId)
    }
    fun containsPlayer(player: Player) : Boolean = playerList.contains(player.uniqueId)
    fun getAllPlayers() : MutableList<UUID> = playerList

    fun onChat(block: (AsyncChatEvent) -> Unit) {
        listen<AsyncChatEvent> {
            if (playerList.contains(it.player.uniqueId)) {
                it.isCancelled = true

                block.invoke(it)

                playerList.forEach { uuid: UUID ->
                    if (Bukkit.getOfflinePlayer(uuid).isOnline) {
                        Bukkit.getPlayer(uuid)?.sendMessage(it.message())
                    }
                }
            }
        }
    }
}