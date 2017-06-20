package io.funatwork.gcm

import io.funatwork.gcm.model.GameOverModel
import io.funatwork.gcm.model.NewGoalModel

/**
 * Parse a push message
 */
class PushParser {

    fun parse(data: Map<String, String>) =
            when (data["type"]) {
                "NEW_GOAL" -> parseNewGoal(data)
                "GAME_OVER" -> parseGameOver(data)
                else -> null
            }

    fun parseNewGoal(data: Map<String, String>) =
            NewGoalModel(gameId = data[PushMessageKeys.KEY_GAME_ID]?.toInt() ?: -1,
                    blueScore = data[PushMessageKeys.KEY_BLUE_SCORE]?.toInt() ?: -1,
                    redScore = data[PushMessageKeys.KEY_RED_SCORE]?.toInt() ?: -1,
                    strikerId = data[PushMessageKeys.KEY_STRIKER_ID]?.toInt() ?: -1)

    fun parseGameOver(data: Map<String, String>) =
            GameOverModel(gameId = data[PushMessageKeys.KEY_GAME_ID]?.toInt() ?: -1,
                    blueScore = data[PushMessageKeys.KEY_BLUE_SCORE]?.toInt() ?: -1,
                    redScore = data[PushMessageKeys.KEY_RED_SCORE]?.toInt() ?: -1,
                    status = data[PushMessageKeys.KEY_GAME_STATUS]?.toInt() ?: -1)
}