package io.funatwork.presenter

import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.GetGameList
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.toModel
import io.funatwork.view.HistoryView
import io.funatwork.view.adapter.GameAdapter

class HistoryPresenter(val historyView: HistoryView, val getGameList: GetGameList) : Presenter, GameAdapter.OnItemClickListener {

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {}

    /**
     * Initializes the presenter by start retrieving the history list.
     */
    fun initialize() {
        loadGames()
    }

    /**
     * Load games
     */
    private fun loadGames() {
        historyView.showLoading()
        getGameList.execute(GameListObserver(historyView), NoParams())
    }

    override fun onGameItemClicked(game: GameModel) {
        // TODO("not implemented")
    }

    private class GameListObserver(val historyView: HistoryView) : DefaultObserver<List<Game>>() {

        override fun onComplete() {
            historyView.hideLoading()
        }

        override fun onError(e: Throwable?) {
            historyView.hideLoading()
            historyView.showError(title = "Error happen", message = e?.message ?: "Unknown Error")
        }

        override fun onNext(games: List<Game>) {
            historyView.renderGameList(games.map(Game::toModel).filter { it.status == 2 })
        }
    }
}