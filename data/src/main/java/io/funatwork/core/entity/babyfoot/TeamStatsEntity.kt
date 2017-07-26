package io.funatwork.core.entity.babyfoot

import io.funatwork.core.entity.PlayerEntity

/**
 * Statistics by team
 */
data class TeamStatsEntity(val player1: PlayerEntity, val player2: PlayerEntity, val gamePlayed: Int, val victory: Int, val loose: Int, val goalAverage: Int, val eloRanking: Double)