package io.funatwork.gcm.model

/**
 * Notification information about a finished game.
 */
data class GameOverModel(val gameId: Int, val blueScore: Int, val redScore: Int, val status: Int) : PushModel