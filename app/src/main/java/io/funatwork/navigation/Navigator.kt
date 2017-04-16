package io.funatwork.navigation

import android.content.Context
import android.content.Intent
import io.funatwork.core.entity.babyfoot.GameEntity
import io.funatwork.view.activity.CreateGameActivity
import io.funatwork.view.activity.GameActivity
import io.funatwork.view.activity.SelectPlayerActivity


/**
 * Created by mmaillot on 3/29/17.
 */
class Navigator {

    fun navigateToCreateGame(context: Context) {
        val intentToLaunch = Intent(context, CreateGameActivity::class.java)
        context.startActivity(intentToLaunch)
    }

    fun navigateToGame(context: Context, pGameEntity: GameEntity) {
        val intentToLaunch = Intent(context, GameActivity::class.java)
        intentToLaunch.putExtra("GAME", pGameEntity.id)
        context.startActivity(intentToLaunch)
    }

    fun navigateToSelectPlayer(context: Context) {
        val intentToLaunch = Intent(context, SelectPlayerActivity::class.java)
        context.startActivity(intentToLaunch)
    }

}