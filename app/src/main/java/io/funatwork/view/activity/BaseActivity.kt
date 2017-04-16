package io.funatwork.view.activity

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import io.funatwork.navigation.Navigator

/**
 * Created by mmaillot on 3/29/17.
 */
abstract class BaseActivity : AppCompatActivity() {

    val navigator = Navigator()

    fun showProgress(): SweetAlertDialog {
        val pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.progressHelper.barColor = Color.parseColor("#A5DC86")
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)
        pDialog.show()
        return pDialog
    }

    fun dismissProgress(dialog: SweetAlertDialog) {
        dialog.dismissWithAnimation()
    }
}