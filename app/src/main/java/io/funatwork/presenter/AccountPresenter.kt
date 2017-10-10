package io.funatwork.presenter

import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.GetPlayerStats
import io.funatwork.domain.interactor.GetUserAuth
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.interactor.params.PlayerStatsParam
import io.funatwork.domain.model.UserAuth
import io.funatwork.domain.model.babyfoot.PlayerStats
import io.funatwork.model.babyfoot.toModel
import io.funatwork.model.toModel
import io.funatwork.view.AccountView

class AccountPresenter(private val accountView: AccountView, private val getUserAuth: GetUserAuth,
                       private val getPlayerStats: GetPlayerStats) : Presenter {

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {}

    fun load() {
        accountView.showLoading()
        getUserAuth.execute(LoadProfileObserver(accountView), NoParams())
    }

    fun loadStats(playerId: Int) {
        getPlayerStats.execute(GetPlayerStatObserver(accountView), PlayerStatsParam(playerId))
    }

    private class LoadProfileObserver(val accountView: AccountView) : DefaultObserver<UserAuth>() {

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

    private class GetPlayerStatObserver(val accountView: AccountView) : DefaultObserver<PlayerStats>() {

        override fun onComplete() {
        }

        override fun onError(exception: Throwable?) {
            accountView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: PlayerStats) {
            accountView.renderPlayerStats(element.toModel())
        }
    }
}