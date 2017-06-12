package io.funatwork.view.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import io.funatwork.FwtApplication
import io.funatwork.navigation.Navigator

abstract class BaseActivity : AppCompatActivity() {

    val fwtApplication by lazy {
        application as FwtApplication
    }

    val navigator = Navigator()
    var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate(savedInstanceState)
    }

    fun showLoading() {
        val dialog = ProgressDialog(this)
        dialog.isIndeterminate = true
        dialog.setCancelable(false)
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        dialog.setMessage("Loading")
        dialog.show()
        this.dialog = dialog
    }

    fun hideLoading() {
        dialog?.hide()
    }

    fun context() = this

    fun showError(title: String, message: String) {
        AlertDialog.Builder(this).setTitle(title).setMessage(message).show()
    }
}