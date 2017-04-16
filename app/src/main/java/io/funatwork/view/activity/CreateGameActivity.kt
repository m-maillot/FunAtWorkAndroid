package io.funatwork.view.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import cn.pedant.SweetAlert.SweetAlertDialog
import io.funatwork.R
import io.funatwork.core.ApiClient
import io.funatwork.core.entity.PlayerEntity
import io.funatwork.core.entity.babyfoot.TeamEntity


class CreateGameActivity : BaseActivity() {

    val imgRedAttackPlayer by lazy {
        findViewById(R.id.img_red_attack_player) as ImageView
    }
    val imgRedDefensePlayer by lazy {
        findViewById(R.id.img_red_defense_player) as ImageView
    }
    val imgBlueAttackPlayer by lazy {
        findViewById(R.id.img_blue_attack_player) as ImageView
    }
    val imgBlueDefensePlayer by lazy {
        findViewById(R.id.img_blue_defense_player) as ImageView
    }

    val btnStarGame by lazy {
        findViewById(R.id.btn_start_game) as Button
    }

    val clientApi by lazy {
        ApiClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)


        imgRedAttackPlayer.setOnClickListener {
            navigator.navigateToSelectPlayer(this)
        }
        imgRedDefensePlayer.setOnClickListener {
            navigator.navigateToSelectPlayer(this)
        }
        imgBlueAttackPlayer.setOnClickListener {
            navigator.navigateToSelectPlayer(this)
        }
        imgBlueDefensePlayer.setOnClickListener {
            navigator.navigateToSelectPlayer(this)
        }

        btnStarGame.setOnClickListener {
            val createGameDiag = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
            createGameDiag.progressHelper.barColor = Color.parseColor("#A5DC86")
            createGameDiag.titleText = "Loading"
            createGameDiag.setCancelable(false)
            createGameDiag.show()
            if (redTeamAttack.selectedItem != null
                    && redTeamDefense.selectedItem != null
                    && blueTeamAttack.selectedItem != null
                    && blueTeamDefense.selectedItem != null) {
                val blueTeam = TeamEntity(pAttackPlayerEntity = blueTeamAttack.selectedItem as PlayerEntity, pDefensePlayerEntity = blueTeamDefense.selectedItem as PlayerEntity)
                val redTeam = TeamEntity(pAttackPlayerEntity = redTeamAttack.selectedItem as PlayerEntity, pDefensePlayerEntity = redTeamDefense.selectedItem as PlayerEntity)
                clientApi.startGame(redTeam, blueTeam, { apiResponse, game ->
                    if (!apiResponse.hasFailed() && game != null) {
                        navigator.navigateToGame(this, game)
                    } else {
                        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Something went wrong! (${apiResponse.message})")
                                .show()
                    }
                })
            }
        }
    }
}
