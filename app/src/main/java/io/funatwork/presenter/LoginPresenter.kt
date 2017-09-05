package io.funatwork.presenter

import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.Signin
import io.funatwork.domain.interactor.params.LoginParam
import io.funatwork.domain.model.UserAuth
import io.funatwork.model.toModel
import io.funatwork.view.LoginView

class LoginPresenter(private val loginView: LoginView, private val signin: Signin) : Presenter {

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {

    }

    fun signin(login: String, password: String) {
        loginView.showLoading()
        signin.execute(SigninObserver(loginView), LoginParam(login, password))
    }

    private class SigninObserver(val loginView: LoginView) : DefaultObserver<UserAuth>() {

        override fun onComplete() {
            loginView.hideLoading()
        }

        override fun onError(exception: Throwable?) {
            loginView.hideLoading()
            loginView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: UserAuth) {
            loginView.onLoginSuccessful(element.toModel())
        }
    }
}