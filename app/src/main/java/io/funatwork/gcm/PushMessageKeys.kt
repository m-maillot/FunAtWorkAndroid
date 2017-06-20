package io.funatwork.gcm

import io.funatwork.BuildConfig

/**
 * Constants for broadcast messages into the app.
 */
object PushMessageKeys {

    const val ACTION_NEW_GOAL = "${BuildConfig.APPLICATION_ID}.ACTION_NEW_GOAL"
    const val ACTION_GAME_OVER = "${BuildConfig.APPLICATION_ID}.ACTION_GAME_OVER"

    const val KEY_BLUE_SCORE = "${BuildConfig.APPLICATION_ID}.KEY_BLUE_SCORE"
    const val KEY_RED_SCORE = "${BuildConfig.APPLICATION_ID}.KEY_RED_SCORE"
    const val KEY_STRIKER_ID = "${BuildConfig.APPLICATION_ID}.KEY_STRIKER_ID"
    const val KEY_GAME_ID = "${BuildConfig.APPLICATION_ID}.KEY_GAME_ID"
    const val KEY_GAME_STATUS = "${BuildConfig.APPLICATION_ID}.KEY_GAME_STATUS"

    const val EXTRA_DATA = "${BuildConfig.APPLICATION_ID}.EXTRA_DATA"
}