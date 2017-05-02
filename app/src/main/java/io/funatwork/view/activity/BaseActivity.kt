package io.funatwork.view.activity

import android.graphics.Color
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import io.funatwork.FwtApplication
import io.funatwork.R
import io.funatwork.SplashscreenActivity
import io.funatwork.navigation.Navigator

abstract class BaseActivity : AppCompatActivity() {

    val fwtApplication by lazy {
        application as FwtApplication
    }

    val navigator = Navigator()
    var dialog: SweetAlertDialog? = null

    fun showLoading() {
        val dialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        dialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        dialog.titleText = "Loading"
        dialog.setCancelable(false)
        dialog.show()
        this.dialog = dialog;
    }

    fun hideLoading() {
        dialog?.hide()
    }

    fun context() = this

    fun showError(title: String, message: String) {
        SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .show()
    }
}