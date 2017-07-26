package io.funatwork.model.babyfoot

import io.funatwork.model.PlayerModel

/**
 * Statistics by team
 */
data class TeamStatsModel(val player1: PlayerModel, val player2: PlayerModel, val gamePlayed: Int, val victory: Int, val loose: Int, val goalAverage: Int, val eloRanking: Double)