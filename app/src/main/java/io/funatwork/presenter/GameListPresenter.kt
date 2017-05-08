package io.funatwork.presenter

import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.GetGameList
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.toModel
import io.funatwork.view.GameListView
import io.funatwork.view.adapter.GameAdapter

class GameListPresenter(val gameListView: GameListView, val getGameList: GetGameList) : Presenter, GameAdapter.OnItemClickListener {

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {
        this.getGameList.dispose()
    }

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
        gameListView.showLoading()
        getGameList.execute(GameListObserver(gameListView), NoParams())
    }

    override fun onGameItemClicked(game: GameModel) {
        // TODO("not implemented")
    }

    private class GameListObserver(val gameListView: GameListView) : DefaultObserver<List<Game>>() {

        override fun onComplete() {
            gameListView.hideLoading()
        }

        override fun onError(e: Throwable?) {
            gameListView.hideLoading()
            gameListView.showError(title = "Error happen", message = e?.message ?: "Unknown Error")
        }

        override fun onNext(games: List<Game>) {
            val currentGames = games.map(Game::toModel).filter { it.status == 1 }
            if (currentGames.isNotEmpty()) {
                gameListView.renderCurrentGame(currentGames[0])
            } else {
                gameListView.renderCurrentGame(null)
            }
            gameListView.renderGameFinishedList(games.map(Game::toModel).filter { it.status != 1 })
        }
    }
}