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
                     val endedDate: DateTime) : Serializable {
    val GAME_PLANNED = 0
    val GAME_STARTED = 1
    val GAME_OVER = 2
    val GAME_CANCELED = 3
}