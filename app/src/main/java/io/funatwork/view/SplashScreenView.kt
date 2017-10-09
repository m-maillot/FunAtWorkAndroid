package io.funatwork.view

import io.funatwork.model.UserAuthModel

interface SplashScreenView : LoadDataView {

    fun goToLoginScreen()

    fun goToMainScreen(userAuth: UserAuthModel)
}