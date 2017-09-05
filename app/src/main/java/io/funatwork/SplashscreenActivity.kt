package io.funatwork

import android.os.Bundle
import io.funatwork.view.activity.BaseActivity

class SplashscreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (fwtApplication.userAuth == null) {
            navigator.navigateToLogin(this)
        } else {
            navigator.navigateToMain(this)
        }
        finish()
    }

}
