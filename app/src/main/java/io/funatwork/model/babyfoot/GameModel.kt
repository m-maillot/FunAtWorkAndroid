package io.funatwork.model.babyfoot

import org.joda.time.DateTime
import java.io.Serializable

data class GameModel(val id: Int,
                     val startedDate: DateTime,
                     val plannedDate: DateTime,
                     val redTeam: TeamModel,
                     val blueTeam: TeamModel,
                     val redTeamGoal: Int,
                     val blueTeamGoal: Int,
                     val goals: List<GoalModel>,
                     val status: Int,
                     val endedDate: DateTime,
                     val tournamentId: Int,
                     val winnerTeam: TeamModel = if (redTeamGoal > blueTeamGoal) redTeam else blueTeam,
                     val looserTeam: TeamModel = if (redTeamGoal < blueTeamGoal) redTeam else blueTeam,
                     val winnerScore: Int = Math.max(redTeamGoal, blueTeamGoal),
                     val looserScore: Int = Math.min(redTeamGoal, blueTeamGoal)) : Serializable {

    companion object Status {
        val PLANNED = 0
        val STARTED = 1
        val GAME_OVER = 2
        val CANCELED = 3
    }

    fun isRedTeamWin() =
            redTeamGoal > blueTeamGoal
}