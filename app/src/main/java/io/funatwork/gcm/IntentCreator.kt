package io.funatwork.gcm

import android.content.Intent
import io.funatwork.gcm.model.GameOverModel
import io.funatwork.gcm.model.NewGoalModel
import io.funatwork.gcm.model.PushModel

/**
 * Create intent for associated data provided by push
 */
class IntentCreator {

    fun create(model: PushModel?): Intent? =
            if (model == null)
                null
            else
                Intent().setAction(getAction(model)).putExtra(PushMessageKeys.EXTRA_DATA, model)

    private fun getAction(model: PushModel?): String =
            when (model) {
                is NewGoalModel -> PushMessageKeys.ACTION_NEW_GOAL
                is GameOverModel -> PushMessageKeys.ACTION_GAME_OVER
                else -> "UNKNOWN"
            }
}