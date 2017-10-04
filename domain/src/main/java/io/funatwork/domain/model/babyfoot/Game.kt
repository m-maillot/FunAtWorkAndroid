package io.funatwork.domain.model.babyfoot

import org.joda.time.DateTime

data class Game(val id: Int,
                val startedDate: DateTime?,
                val plannedDate: DateTime?,
                val redTeam: Team?,
                val blueTeam: Team?,
                val redTeamGoal: Int?,
                val blueTeamGoal: Int?,
                val goals: List<Goal>,
                val status: Int,
                val endedDate: DateTime?) {
    val GAME_PLANNED = 0
    val GAME_STARTED = 1
    val GAME_OVER = 2
    val GAME_CANCELED = 3
}