package io.funatwork.navigation

import android.content.Context
import android.content.Intent
import io.funatwork.core.model.babyfoot.Game
import io.funatwork.view.activity.CreateGameActivity
import io.funatwork.view.activity.GameActivity


/**
 * Created by mmaillot on 3/29/17.
 */
class Navigator {

    fun navigateToCreateGame(context: Context) {
        val intentToLaunch = Intent(context, CreateGameActivity::class.java)
        context.startActivity(intentToLaunch)
    }

    fun navigateToGame(context: Context, game: Game) {
        val intentToLaunch = Intent(context, GameActivity::class.java)
        intentToLaunch.putExtra("GAME", game.id)
        context.startActivity(intentToLaunch)
    }

}