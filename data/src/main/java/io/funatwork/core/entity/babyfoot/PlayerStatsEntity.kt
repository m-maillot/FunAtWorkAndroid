package io.funatwork.core.entity.babyfoot

import io.funatwork.core.entity.PlayerEntity

/**
 * Player statistics
 */
data class PlayerStatsEntity(val player: PlayerEntity,
                             val rank: Int,
                             val gamePlayed: Int,
                             val victory: Int,
                             val loose: Int,
                             val goals: Int,
                             val goalAverage: Int,
                             val eloRanking: Double)