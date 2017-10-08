package io.funatwork.navigation

import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import io.funatwork.R
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.TeamModel
import io.funatwork.view.activity.*


class Navigator {

    fun navigateToMain(context: Context, flags: Int = 0) {
        val intentToLaunch = Intent(context, MainActivity::class.java)
        intentToLaunch.putExtra("SECTION", R.id.action_play)
        intentToLaunch.flags = flags
        context.startActivity(intentToLaunch)
    }

    fun navigateToTournament(context: Context, flags: Int = 0) {
        val intentToLaunch = Intent(context, MainActivity::class.java)
        intentToLaunch.putExtra("SECTION", R.id.action_tournament)
        intentToLaunch.flags = flags
        context.startActivity(intentToLaunch)
    }

    fun navigateToLogin(context: Context, flags: Int = 0) {
        val intentToLaunch = Intent(context, LoginActivity::class.java)
        intentToLaunch.flags = flags
        context.startActivity(intentToLaunch)
    }

    fun navigateToHome(context: Context, flags: Int = 0) {
        val intentToLaunch = Intent(context, MainActivity::class.java)
        intentToLaunch.flags = flags
        context.startActivity(intentToLaunch)
    }

    fun navigateToCreateGame(context: Context, flags: Int = 0, teamRed: TeamModel? = null, teamBlue: TeamModel? = null) {
        val intentToLaunch = Intent(context, SelectPlayerActivity::class.java)
        if (teamBlue != null && teamRed != null) {
            intentToLaunch.putExtra("redAttackId", teamRed.attackPlayer.id)
            intentToLaunch.putExtra("redDefenseId", teamRed.defensePlayer.id)
            intentToLaunch.putExtra("blueAttackId", teamBlue.attackPlayer.id)
            intentToLaunch.putExtra("blueDefenseId", teamBlue.defensePlayer.id)
        }
        intentToLaunch.flags = flags
        context.startActivity(intentToLaunch)
    }

    fun navigateToStartGame(context: Context, redTeam: TeamModel, blueTeam: TeamModel, activityOptionsCompat: ActivityOptionsCompat) {
        val intentToLaunch = Intent(context, StartGameActivity::class.java)
        intentToLaunch.putExtra("BLUE_TEAM", blueTeam)
        intentToLaunch.putExtra("RED_TEAM", redTeam)
        context.startActivity(intentToLaunch, activityOptionsCompat.toBundle())
    }

    fun navigateToGame(context: Context, gameModel: GameModel, activityOptionsCompat: ActivityOptionsCompat? = null) {
        val intentToLaunch = Intent(context, GameActivity::class.java)
        intentToLaunch.putExtra("GAME", gameModel.id)
        if (activityOptionsCompat != null) {
            context.startActivity(intentToLaunch, activityOptionsCompat.toBundle())
        } else {
            context.startActivity(intentToLaunch)
        }
    }

    fun navigateToGameOver(context: Context, gameModel: GameModel, activityOptionsCompat: ActivityOptionsCompat) {
        val intentToLaunch = Intent(context, GameOverActivity::class.java)
        intentToLaunch.putExtra("GAME", gameModel)
        context.startActivity(intentToLaunch, activityOptionsCompat.toBundle())
    }
}