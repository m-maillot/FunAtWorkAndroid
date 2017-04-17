package io.funatwork.presenter

import io.funatwork.domain.interactor.AddGoal
import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.LoadGame
import io.funatwork.domain.interactor.params.AddGoalParam
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.model.PlayerModel
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.toBo
import io.funatwork.model.babyfoot.toModel
import io.funatwork.model.toBo
import io.funatwork.view.GameView

class GamePresenter(val gameView: GameView, val loadGame: LoadGame, val addGoal: AddGoal) : Presenter {

    override fun resume() {
    }

    override fun pause() {
    }

    override fun destroy() {
    }

    /**
     * Initializes the presenter by start retrieving the players list.
     */
    fun initialize(gameId: Int) {
        loadGame(gameId)
    }

    /**
     * Loads game
     */
    private fun loadGame(gameId: Int) {
        gameView.showLoading()
        loadGame.execute(GameObserver(gameView), gameId)
    }

    fun addGoal(game: GameModel, player: PlayerModel) =
            addGoal.execute(GoalObserver(gameView), AddGoalParam(game.toBo(), player.toBo()))

    private class GameObserver(val gameView: GameView) : DefaultObserver<Game>() {

        override fun onComplete() {
            gameView.hideLoading()
        }

        override fun onError(e: Throwable?) {
            gameView.hideLoading()
            gameView.showError(title = "Error happen", message = e?.message ?: "Unknown Error")
        }

        override fun onNext(game: Game) {
            gameView.renderGame(game.toModel())
        }
    }

    private class GoalObserver(val gameView: GameView) : DefaultObserver<Game>() {

        override fun onComplete() {
        }

        override fun onError(e: Throwable?) {
            gameView.showError(title = "Error happen", message = e?.message ?: "Unknown Error")
        }

        override fun onNext(game: Game) {
            gameView.renderGoal(game.toModel())
            if (game.status == game.GAME_OVER) {
                gameView.renderGameFinished(game.toModel())
            }
        }
    }
}