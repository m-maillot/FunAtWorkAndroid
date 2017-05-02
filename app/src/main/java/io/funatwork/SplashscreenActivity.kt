package io.funatwork

import android.os.Bundle
import io.funatwork.view.activity.BaseActivity

class SplashscreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        supportActionBar?.title = getString(R.string.app_name_long)

        navigator.navigateToMain(this)
    }
}
