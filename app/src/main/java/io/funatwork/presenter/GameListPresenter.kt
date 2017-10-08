package io.funatwork.presenter

import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.GetGameList
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.model.babyfoot.toModel
import io.funatwork.view.GameListView

class GameListPresenter(val gameListView: GameListView, val getGameList: GetGameList) : Presenter {

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

    private class GameListObserver(val gameListView: GameListView) : DefaultObserver<List<Game>>() {

        override fun onComplete() {
            gameListView.hideLoading()
        }

        override fun onError(exception: Throwable?) {
            gameListView.hideLoading()
            gameListView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: List<Game>) {
            val currentGames = element.map(Game::toModel).filter { it.status == 1 }
            if (currentGames.isNotEmpty()) {
                gameListView.renderCurrentGame(currentGames[0])
            } else {
                gameListView.renderCurrentGame(null)
            }
            gameListView.renderGameFinishedList(element.map(Game::toModel).filter { it.status != 1 })
        }
    }
}