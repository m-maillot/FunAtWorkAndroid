package io.funatwork

import android.os.Bundle
import io.funatwork.view.activity.BaseActivity

class SplashscreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        supportActionBar?.title = getString(R.string.app_name_long)

        val button = findViewById(R.id.button)
        button.setOnClickListener {
            navigator.navigateToCreateGame(this)
        }
        bottomNavigationView?.selectedItemId = R.id.action_play
    }
}
