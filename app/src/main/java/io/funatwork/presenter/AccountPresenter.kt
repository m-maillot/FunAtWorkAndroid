package io.funatwork.presenter

import io.funatwork.core.exception.NotLoggedException
import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.GetUserAuth
import io.funatwork.domain.interactor.Signin
import io.funatwork.domain.interactor.params.LoginParam
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.UserAuth
import io.funatwork.model.toModel
import io.funatwork.view.AccountView

class AccountPresenter(private val accountView: AccountView, private val getUserAuth: GetUserAuth, private val signin: Signin) : Presenter {

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {

    }

    fun load() {
        accountView.showLoading()
        getUserAuth.execute(LoadProfileObserver(accountView), NoParams())
    }

    fun signin(login: String, password: String) {
        accountView.showLoading()
        signin.execute(SigninObserver(accountView), LoginParam(login, password))
    }

    private class LoadProfileObserver(val accountView: AccountView) : DefaultObserver<UserAuth>() {

        override fun onComplete() {
            accountView.hideLoading()
        }

        override fun onError(exception: Throwable?) {
            accountView.hideLoading()
            if (exception is NotLoggedException) {
                accountView.renderNotConnected()
            } else {
                accountView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
            }
        }

        override fun onNext(element: UserAuth) {
            accountView.renderAccount(element.toModel())
        }
    }

    private class SigninObserver(val accountView: AccountView) : DefaultObserver<UserAuth>() {

        override fun onComplete() {
            accountView.hideLoading()
        }

        override fun onError(exception: Throwable?) {
            accountView.hideLoading()
            accountView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: UserAuth) {
            accountView.renderAccount(element.toModel())
        }
    }
}