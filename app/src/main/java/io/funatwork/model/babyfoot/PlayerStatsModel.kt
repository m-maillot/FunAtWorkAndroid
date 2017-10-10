package io.funatwork.model.babyfoot

import io.funatwork.model.PlayerModel

/**
 * Player statistics
 */
data class PlayerStatsModel(val player: PlayerModel,
                            val rank: Int,
                            val gamePlayed: Int,
                            val victory: Int,
                            val loose: Int,
                            val goals: Int,
                            val goalAverage: Int,
                            val eloRanking: Int)