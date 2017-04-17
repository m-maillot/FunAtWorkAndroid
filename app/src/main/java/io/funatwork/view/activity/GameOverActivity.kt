package io.funatwork.view.activity

import android.os.Bundle
import android.widget.Button
import io.funatwork.R

class GameOverActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        val restart = findViewById(R.id.btn_new_game) as Button
        restart.setOnClickListener {
            navigator.navigateToCreateGame(this)
        }
    }
}
