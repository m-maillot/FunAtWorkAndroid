package io.funatwork.navigation

import android.content.Context
import android.content.Intent
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.TeamModel
import io.funatwork.view.activity.CreateGameActivity
import io.funatwork.view.activity.GameActivity
import io.funatwork.view.activity.GameOverActivity
import io.funatwork.view.activity.StartGameActivity
import android.support.v4.app.ActivityOptionsCompat



class Navigator {

    fun navigateToCreateGame(context: Context) {
        val intentToLaunch = Intent(context, CreateGameActivity::class.java)
        context.startActivity(intentToLaunch)
    }

    fun navigateToStartGame(context: Context, redTeam: TeamModel, blueTeam: TeamModel, activityOptionsCompat: ActivityOptionsCompat) {
        val intentToLaunch = Intent(context, StartGameActivity::class.java)
        intentToLaunch.putExtra("BLUE_TEAM", blueTeam)
        intentToLaunch.putExtra("RED_TEAM", redTeam)
        context.startActivity(intentToLaunch, activityOptionsCompat.toBundle())
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