package io.funatwork.model.babyfoot

import io.funatwork.domain.model.babyfoot.GameMode
import io.funatwork.domain.model.babyfoot.GameStatus
import io.funatwork.model.PlayerModel
import org.joda.time.DateTime
import java.io.Serializable

data class GameModel(val id: Int,
                     val creator: PlayerModel,
                     val startedDate: DateTime,
                     val plannedDate: DateTime,
                     val redTeam: TeamModel,
                     val blueTeam: TeamModel,
                     val redTeamGoal: Int,
                     val blueTeamGoal: Int,
                     val goals: List<GoalModel>,
                     val status: GameStatus,
                     val endedDate: DateTime,
                     val tournamentId: Int,
                     val mode: GameMode,
                     val modeLimitValue: Int) : Serializable {

    fun getWinnerTeam() =
            if (redTeamGoal > blueTeamGoal) redTeam else blueTeam

    fun getLooserTeam() =
            if (redTeamGoal < blueTeamGoal) redTeam else blueTeam

    fun getWinnerScore() =
            Math.max(redTeamGoal, blueTeamGoal)

    fun getLooserScore() =
            Math.min(redTeamGoal, blueTeamGoal)

    fun isRedTeamWin() =
            redTeamGoal > blueTeamGoal
}