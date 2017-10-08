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
                val endedDate: DateTime?,
                val tournamentId: Int?)