package io.funatwork.view

/**
 * Created by mmaillot on 8/31/17.
 */
interface LoginView : LoadDataView {

    fun onLoginFailed()

    fun onLoginSuccessful()
}