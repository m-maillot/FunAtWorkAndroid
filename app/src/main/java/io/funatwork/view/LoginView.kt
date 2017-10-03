package io.funatwork.view

import io.funatwork.model.UserAuthModel

interface LoginView : LoadDataView {

    fun onLoginFailed()

    fun onLoginSuccessful(userAuthModel: UserAuthModel)
}