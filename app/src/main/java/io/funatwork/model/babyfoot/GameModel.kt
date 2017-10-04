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
                     val endedDate: DateTime) : Serializable