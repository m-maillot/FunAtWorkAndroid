package io.funatwork.navigation

import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.TeamModel
import io.funatwork.view.activity.*


class Navigator {

    fun navigateToMain(context: Context) {
        val intentToLaunch = Intent(context, MainActivity::class.java)
        context.startActivity(intentToLaunch)
    }

    fun navigateToHome(context: Context, flags: Int = 0) {
        val intentToLaunch = Intent(context, MainActivity::class.java)
        intentToLaunch.flags = flags
        context.startActivity(intentToLaunch)
    }

    fun navigateToCreateGame(context: Context, flags: Int = 0) {
        val intentToLaunch = Intent(context, SelectPlayerActivity::class.java)
        intentToLaunch.flags = flags
        context.startActivity(intentToLaunch)
    }

    fun navigateToStartGame(context: Context, redTeam: TeamModel, blueTeam: TeamModel, activityOptionsCompat: ActivityOptionsCompat) {
        val intentToLaunch = Intent(context, StartGameActivity::class.java)
        intentToLaunch.putExtra("BLUE_TEAM", blueTeam)
        intentToLaunch.putExtra("RED_TEAM", redTeam)
        context.startActivity(intentToLaunch, activityOptionsCompat.toBundle())
    }

    fun navigateToGame(context: Context, gameModel: GameModel, activityOptionsCompat: ActivityOptionsCompat) {
        val intentToLaunch = Intent(context, GameActivity::class.java)
        intentToLaunch.putExtra("GAME", gameModel.id)
        context.startActivity(intentToLaunch, activityOptionsCompat.toBundle())
    }

    fun navigateToGameOver(context: Context, gameModel: GameModel, activityOptionsCompat: ActivityOptionsCompat) {
        val intentToLaunch = Intent(context, GameOverActivity::class.java)
        intentToLaunch.putExtra("GAME", gameModel)
        context.startActivity(intentToLaunch, activityOptionsCompat.toBundle())
    }
}