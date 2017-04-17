package io.funatwork.model.babyfoot

import io.funatwork.model.babyfoot.GoalModel
import io.funatwork.model.babyfoot.TeamModel

data class GameModel(val id: Int,
                     val beginTimestampInSeconds: Long,
                     val redTeam: TeamModel,
                     val blueTeam: TeamModel,
                     val redTeamGoal: Int,
                     val blueTeamGoal: Int,
                     val goals: List<GoalModel>,
                     val status: Int,
                     val ended: Long)