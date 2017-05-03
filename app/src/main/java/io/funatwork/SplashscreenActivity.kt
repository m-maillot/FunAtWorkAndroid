package io.funatwork

import android.os.Bundle
import io.funatwork.view.activity.BaseActivity

class SplashscreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.navigateToMain(this)
        finish()
    }
}
