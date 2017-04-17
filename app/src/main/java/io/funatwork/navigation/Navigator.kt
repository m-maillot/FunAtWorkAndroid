package io.funatwork.navigation

import android.content.Context
import android.content.Intent
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.view.activity.CreateGameActivity
import io.funatwork.view.activity.GameActivity
import io.funatwork.view.activity.GameOverActivity

class Navigator {

    fun navigateToCreateGame(context: Context) {
        val intentToLaunch = Intent(context, CreateGameActivity::class.java)
        context.startActivity(intentToLaunch)
    }

    fun navigateToGame(context: Context, gameModel: GameModel) {
        val intentToLaunch = Intent(context, GameActivity::class.java)
        intentToLaunch.putExtra("GAME", gameModel.id)
        context.startActivity(intentToLaunch)
    }

    fun navigateToGameOver(context: Context, gameModel: GameModel) {
        val intentToLaunch = Intent(context, GameOverActivity::class.java)
        intentToLaunch.putExtra("GAME", gameModel.id)
        context.startActivity(intentToLaunch)
    }
}