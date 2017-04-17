package io.funatwork.domain.model.babyfoot

/**
 * Created by mmaillot on 3/26/17.
 */
data class Game(val id: Int,
                val beginTimestampInSeconds: Long,
                val redTeam: Team,
                val blueTeam: Team,
                val redTeamGoal: Int,
                val blueTeamGoal: Int,
                val goals: List<Goal>,
                val status: Int,
                val ended: Long) {
    val GAME_STARTED = 1
    val GAME_OVER = 2
    val GAME_CANCELED = 3
}