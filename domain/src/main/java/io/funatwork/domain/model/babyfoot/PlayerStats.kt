package io.funatwork.domain.model.babyfoot

import io.funatwork.domain.model.Player

/**
 * Player statistics
 */
data class PlayerStats(val player: Player,
                       val rank: Int,
                       val gamePlayed: Int,
                       val victory: Int,
                       val loose: Int,
                       val goals: Int,
                       val goalAverage: Int,
                       val eloRanking: Double)