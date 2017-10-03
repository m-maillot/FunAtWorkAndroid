package io.funatwork.presenter

import io.funatwork.core.exception.NotLoggedException
import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.GetUserAuth
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.UserAuth
import io.funatwork.view.SplashScreenView

class SplashScreenPresenter(val splashScreenView: SplashScreenView, private val getUserAuth: GetUserAuth) : Presenter {

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {}

    fun checkLogin() {
        splashScreenView.showLoading()
        getUserAuth.execute(SplashScreenPresenter.LoadProfileObserver(splashScreenView), NoParams())
    }

    private class LoadProfileObserver(val splashScreenView: SplashScreenView) : DefaultObserver<UserAuth>() {

        override fun onComplete() {
            splashScreenView.hideLoading()
        }

        override fun onError(exception: Throwable?) {
            splashScreenView.hideLoading()
            if (exception is NotLoggedException) {
                splashScreenView.goToLoginScreen()
            } else {
                splashScreenView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
            }
        }

        override fun onNext(element: UserAuth) {
            if (element.expire_at > (System.currentTimeMillis() / 1000)) {
                splashScreenView.goToMainScreen()
            } else {
                splashScreenView.goToLoginScreen()
            }
        }
    }
}