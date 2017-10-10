package io.funatwork.presenter

import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.GetPlayersStats
import io.funatwork.domain.interactor.GetTeamStats
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.babyfoot.PlayerStats
import io.funatwork.domain.model.babyfoot.TeamStats
import io.funatwork.model.babyfoot.toModel
import io.funatwork.view.StandingsView

class StandingsPresenter(val standingsView: StandingsView, val getTeamStats: GetTeamStats, val getPlayersStats: GetPlayersStats) : Presenter {

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {

    }

    /**
     * Load team stats
     */
    fun loadTeamStats() {
        standingsView.showLoading()
        getTeamStats.execute(TeamStatsObserver(standingsView), NoParams())
    }

    /**
     * Load player stats
     */
    fun loadPlayerStats() {
        standingsView.showLoading()
        getPlayersStats.execute(PlayerStatsObserver(standingsView), NoParams())
    }

    private class TeamStatsObserver(val standingsView: StandingsView) : DefaultObserver<List<TeamStats>>() {

        override fun onComplete() {
            standingsView.hideLoading()
        }

        override fun onError(exception: Throwable?) {
            standingsView.hideLoading()
            standingsView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: List<TeamStats>) {
            standingsView.renderTeamStats(element.map(TeamStats::toModel))
        }
    }

    private class PlayerStatsObserver(val standingsView: StandingsView) : DefaultObserver<List<PlayerStats>>() {

        override fun onComplete() {
            standingsView.hideLoading()
        }

        override fun onError(exception: Throwable?) {
            standingsView.hideLoading()
            standingsView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: List<PlayerStats>) {
            standingsView.renderPlayerStats(element.map(PlayerStats::toModel))
        }
    }
}