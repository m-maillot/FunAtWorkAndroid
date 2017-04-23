package io.funatwork.view.activity

import android.graphics.Color
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import io.funatwork.FwtApplication
import io.funatwork.R
import io.funatwork.SplashscreenActivity
import io.funatwork.navigation.Navigator


/**
 * Created by mmaillot on 3/29/17.
 */
abstract class BaseActivity : AppCompatActivity() {

    val bottomNavigationView by lazy {
        findViewById(R.id.bottom_navigation) as BottomNavigationView?
    }

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

    override fun onStart() {
        super.onStart()
        initTab()
    }

    fun initTab() {
        bottomNavigationView?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_play -> {
                    if (this !is SplashscreenActivity) {
                        navigator.navigateToPlay(this)
                    }
                }
                R.id.action_history -> {
                    if (this !is HistoryActivity) {
                        navigator.navigateToHistory(this)
                    }
                }
                R.id.action_stats -> {
                }
            }
            false
        }
    }
}