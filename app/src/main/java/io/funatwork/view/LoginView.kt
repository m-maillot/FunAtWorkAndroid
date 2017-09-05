package io.funatwork.view

import io.funatwork.model.UserAuthModel

/**
 * Created by mmaillot on 8/31/17.
 */
interface LoginView : LoadDataView {

    fun onLoginFailed()

    fun onLoginSuccessful(userAuthModel: UserAuthModel)
}