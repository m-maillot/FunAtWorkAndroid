package io.funatwork.presenter

import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.GetCurrentTournament
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.babyfoot.Tournament
import io.funatwork.model.babyfoot.toModel
import io.funatwork.view.TournamentView

class TournamentPresenter(val tournamentView: TournamentView, val getCurrentTournament: GetCurrentTournament) : Presenter {

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {
        this.getCurrentTournament.dispose()
    }

    /**
     * Initializes the presenter by start retrieving the history list.
     */
    fun initialize() {
        loadCurrentTournament()
    }

    /**
     * Load games
     */
    private fun loadCurrentTournament() {
        tournamentView.showLoading()
        getCurrentTournament.execute(CurrentTournamentObserver(tournamentView), NoParams())
    }

    private class CurrentTournamentObserver(val tournamentView: TournamentView) : DefaultObserver<Tournament>() {

        override fun onComplete() {
            tournamentView.hideLoading()
        }

        override fun onError(exception: Throwable?) {
            tournamentView.hideLoading()
            tournamentView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: Tournament) {
            tournamentView.renderCurrentTournament(element.toModel())
        }
    }
}