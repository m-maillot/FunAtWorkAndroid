package io.funatwork.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import io.funatwork.R
import io.funatwork.core.ApiClient
import io.funatwork.core.entity.babyfoot.GameEntity
import io.funatwork.core.entity.babyfoot.TeamEntity

class GameActivity : AppCompatActivity() {

    val clientApi by lazy {
        ApiClient(this)
    }

    val tvGameId by lazy {
        findViewById(R.id.tv_game_id) as TextView
    }
    val tvRedTeam by lazy {
        findViewById(R.id.tv_team_red) as TextView
    }
    val tvBlueTeam by lazy {
        findViewById(R.id.tv_team_blue) as TextView
    }
    val tvScore by lazy {
        findViewById(R.id.tv_game_score) as TextView
    }

    val btnRedTeamGoal by lazy {
        findViewById(R.id.btn_team_red_goal) as Button
    }
    val btnBlueTeamGoal by lazy {
        findViewById(R.id.btn_team_blue_goal) as Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val gameId = intent.extras.getInt("GAME", -1)
        clientApi.getGame(gameId, { apiResponse, game ->
            if (!apiResponse.hasFailed() && game != null) {
                tvGameId.text = getString(R.string.game_id, game.id.toString())
                tvRedTeam.text = getString(R.string.game_team_red, game.pRedTeamEntity.pAttackPlayerEntity.name, game.pRedTeamEntity.pDefensePlayerEntity.name)
                tvBlueTeam.text = getString(R.string.game_team_blue, game.pBlueTeamEntity.pAttackPlayerEntity.name, game.pBlueTeamEntity.pDefensePlayerEntity.name)
                tvScore.text = getString(R.string.game_score, game.blueTeamGoal.toString(), game.redTeamGoal.toString())
                btnBlueTeamGoal.setOnClickListener {
                    addGoal(game, game.pBlueTeamEntity)
                }
                btnRedTeamGoal.setOnClickListener {
                    addGoal(game, game.pRedTeamEntity)
                }
            } else {
                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .show()
            }
        })


    }

    fun addGoal(pGameEntity: GameEntity, pTeamEntity: TeamEntity) {
        clientApi.addGoal(pGameEntity, pTeamEntity.pAttackPlayerEntity, { apiResponse, game ->
            if (!apiResponse.hasFailed() && game != null) {
                tvScore.text = getString(R.string.game_score, game.blueTeamGoal.toString(), game.redTeamGoal.toString())
            } else {
                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong!")
                        .show()
            }
        })
    }
}
