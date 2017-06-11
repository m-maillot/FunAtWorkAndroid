package io.funatwork.gcm.model

/**
 * Notification information about a finished game.
 */
data class GameOverModel(val gameId: Int, val scoreBlue: Int, val scoreRed: Int, val status: Int) : PushModel