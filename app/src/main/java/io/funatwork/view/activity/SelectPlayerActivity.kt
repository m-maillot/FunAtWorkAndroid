package io.funatwork.view.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import io.funatwork.R
import io.funatwork.core.ApiClient
import io.funatwork.view.PlayerListView
import io.funatwork.view.adapter.PlayerAdapter

class SelectPlayerActivity : BaseActivity(), PlayerListView {

    val rvPlayers by lazy {
        findViewById(R.id.rv_players) as RecyclerView
    }

    val clientApi by lazy {
        ApiClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_player)

        rvPlayers.layoutManager = LinearLayoutManager(this)
        val progress = showProgress()
        clientApi.getPlayers { apiResponse, list ->
            dismissProgress(progress)
            if (!apiResponse.hasFailed()) {
                rvPlayers.adapter = PlayerAdapter(list)
            } else {
                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Something went wrong! (${apiResponse.message})")
                        .show()
            }
        }
    }
}
