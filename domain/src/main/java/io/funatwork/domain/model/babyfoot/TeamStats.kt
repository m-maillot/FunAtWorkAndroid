package io.funatwork.domain.model.babyfoot

import io.funatwork.domain.model.Player

/**
 * Statistics by team
 */
data class TeamStats(val player1: Player, val player2: Player, val gamePlayed: Int, val victory: Int, val loose: Int, val goalAverage: Int, val eloRanking: Double)