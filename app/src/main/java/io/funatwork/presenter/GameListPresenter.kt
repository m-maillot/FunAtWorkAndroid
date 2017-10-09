package io.funatwork.presenter

import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.GetGameList
import io.funatwork.domain.interactor.LoadCurrentGame
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.toModel
import io.funatwork.view.GameListView

class GameListPresenter(val gameListView: GameListView, private val getGameList: GetGameList,
                        private val loadCurrentGame: LoadCurrentGame) : Presenter {

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {
        getGameList.dispose()
        loadCurrentGame.dispose()
    }

    /**
     * Initializes the presenter by start retrieving the history list.
     */
    fun initialize() {
        loadGames()
        loadCurrentGame()
    }

    /**
     * Load games
     */
    private fun loadGames() {
        gameListView.showLoadingGames()
        getGameList.execute(GameListObserver(gameListView), NoParams())
    }

    private fun loadCurrentGame() {
        gameListView.showLoadingCurrentGame()
        loadCurrentGame.execute(CurrentGameObserver(gameListView), NoParams())
    }

    private class GameListObserver(val gameListView: GameListView) : DefaultObserver<List<Game>>() {

        override fun onComplete() {
            gameListView.hideLoadingGames()
        }

        override fun onError(exception: Throwable?) {
            gameListView.hideLoadingGames()
            gameListView.showErrorGames(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: List<Game>) {
            gameListView.renderGameFinishedList(element.map(Game::toModel).filter { it.status == GameModel.GAME_OVER })
        }
    }

    private class CurrentGameObserver(val gameListView: GameListView) : DefaultObserver<Game>() {

        override fun onComplete() {
            gameListView.hideLoadingCurrentGame()
        }

        override fun onError(exception: Throwable?) {
            gameListView.hideLoadingCurrentGame()
            gameListView.showErrorCurrentGames(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: Game) {
            gameListView.renderCurrentGame(element.toModel())
        }
    }
}