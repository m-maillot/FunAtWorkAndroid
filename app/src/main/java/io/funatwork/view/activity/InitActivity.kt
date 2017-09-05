package io.funatwork.view.activity

import android.os.Bundle

class InitActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_init)
        if (fwtApplication.userAuth == null) {
            navigator.navigateToLogin(this)
        } else {
            navigator.navigateToHome(this)
        }
    }
}
