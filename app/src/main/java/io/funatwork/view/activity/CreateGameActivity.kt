package io.funatwork.view.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import cn.pedant.SweetAlert.SweetAlertDialog
import io.funatwork.R
import io.funatwork.core.ApiClient
import io.funatwork.core.model.Player
import io.funatwork.core.model.babyfoot.Team


class CreateGameActivity : BaseActivity() {

    val redTeamAttack by lazy {
        findViewById(R.id.redAttack) as Spinner
    }
    val redTeamDefense by lazy {
        findViewById(R.id.redDefense) as Spinner
    }
    val blueTeamAttack by lazy {
        findViewById(R.id.blueAttack) as Spinner
    }
    val blueTeamDefense by lazy {
        findViewById(R.id.blueDefense) as Spinner
    }

    val btnStarGame by lazy {
        findViewById(R.id.btnStartGame) as Button
    }

    val clientApi by lazy {
        ApiClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)

        val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()

        clientApi.getPlayers { apiResponse, list ->
            pDialog.dismissWithAnimation()
            if (!apiResponse.hasFailed()) {
                val spinnerArrayAdapter = ArrayAdapter(this,
                        android.R.layout.simple_spinner_item, list)
                redTeamAttack.adapter = spinnerArrayAdapter
                redTeamDefense.adapter = spinnerArrayAdapter
                blueTeamAttack.adapter = spinnerArrayAdapter
                blueTeamDefense.adapter = spinnerArrayAdapter
            } else {
                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong! (${apiResponse.message})")
                        .show()
            }
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
                val blueTeam = Team(attackPlayer = blueTeamAttack.selectedItem as Player, defensePlayer = blueTeamDefense.selectedItem as Player)
                val redTeam = Team(attackPlayer = redTeamAttack.selectedItem as Player, defensePlayer = redTeamDefense.selectedItem as Player)
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
