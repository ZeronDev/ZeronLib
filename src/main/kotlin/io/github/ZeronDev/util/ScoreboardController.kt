package io.github.ZeronDev.util

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Scoreboard

class ScoreboardController {
    fun newScoreboard(name: String, vararg score: Pair<String, Int>) : Scoreboard {
        val scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val objective = scoreboard.registerNewObjective("scoreboard_${name}", Criteria.DUMMY, Component.text(name))
        objective.displaySlot = DisplaySlot.SIDEBAR
        score.forEach {
            objective.getScore(it.first).score = it.second
        }
        return scoreboard
    }
    fun Player.setScoreboard(name: String, vararg score: Pair<String, Int>) : Scoreboard {
        val scoreboard = Bukkit.getScoreboardManager().newScoreboard
        val objective = scoreboard.registerNewObjective("scoreboard_${name}", Criteria.DUMMY, Component.text(name))
        objective.displaySlot = DisplaySlot.SIDEBAR
        score.forEach {
            objective.getScore(it.first).score = it.second
        }
        return scoreboard
    }
    fun Player.removeScoreboard() {
        this.scoreboard = Bukkit.getScoreboardManager().newScoreboard
    }
}