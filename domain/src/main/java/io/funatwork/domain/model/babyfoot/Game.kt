package io.funatwork.domain.model.babyfoot

import io.funatwork.domain.model.Player
import org.joda.time.DateTime

data class Game(val id: Int,
                val creator: Player,
                val startedDate: DateTime?,
                val plannedDate: DateTime?,
                val redTeam: Team?,
                val blueTeam: Team?,
                val redTeamGoal: Int?,
                val blueTeamGoal: Int?,
                val goals: List<Goal>,
                val status: GameStatus,
                val endedDate: DateTime?,
                val tournamentId: Int?,
                val mode: GameMode,
                val modeLimitValue: Int)