package io.funatwork.gcm.model

/**
 * Notification information about a new goal
 */
data class NewGoalModel(val gameId: Int, val strikerId: Int, val blueScore: Int, val redScore: Int) : PushModel