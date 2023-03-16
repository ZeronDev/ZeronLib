package io.github.ZeronDev

import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.permissions.Permission
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Scoreboard
import java.util.*

object OtherFunctions {
    fun registerPermission(permission: String) {
        if (Bukkit.getPluginManager().getPermission(permission) == null) {
            Bukkit.getPluginManager().addPermission(Permission(permission))
            Bukkit.reloadPermissions()
        }
    }

    fun newScoreboard(name: String, vararg score: Pair<String, Int>) : Scoreboard {
        val scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val objective = scoreboard.registerNewObjective("scoreboard_${name}", Criteria.DUMMY, text(name))
        objective.displaySlot = DisplaySlot.SIDEBAR
        score.forEach {
            objective.getScore(it.first).score = it.second
        }
        return scoreboard
    }
    fun Player.setScoreboard(name: String, vararg score: Pair<String, Int>) : Scoreboard {
        val scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val objective = scoreboard.registerNewObjective("scoreboard_${name}", Criteria.DUMMY, text(name))
        objective.displaySlot = DisplaySlot.SIDEBAR
        score.forEach {
            objective.getScore(it.first).score = it.second
        }
        return scoreboard
    }
    fun Player.removeScoreboard() {
        this.scoreboard = Bukkit.getScoreboardManager().newScoreboard
    }
    fun Player.isFull(item: ItemStack? = null) : Boolean {
        return if (inventory.firstEmpty() == -1) {
            if (item == null || inventory.all(item).isEmpty()) return true

            val storage = inventory.storageContents

            storage.filter {
                it!!.isSimilar(item)
            }.sumOf { it!!.amount } + item.amount > storage.count {
                it!!.isSimilar(item)
            } * item.maxStackSize
        } else false
    }
}