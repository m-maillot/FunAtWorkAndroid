package io.funatwork.presenter

import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.StartGame
import io.funatwork.domain.interactor.params.CreateGameParam
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.model.babyfoot.TeamModel
import io.funatwork.model.babyfoot.toBo
import io.funatwork.model.babyfoot.toModel
import io.funatwork.view.StartGameView

class StartGamePresenter(val startGameView: StartGameView, val startGame: StartGame) : Presenter {
    override fun resume() {}

    override fun pause() {}

    override fun destroy() {}

    fun startGame(redTeam: TeamModel, blueTeam: TeamModel) {
        startGameView.showLoading()
        startGame.execute(StartGameObserver(startGameView),
                CreateGameParam(redTeam.toBo(), blueTeam.toBo()))
    }

    private class StartGameObserver(val startGameView: StartGameView) : DefaultObserver<Game>() {

        override fun onComplete() {
            startGameView.hideLoading()
        }

        override fun onError(exception: Throwable?) {
            startGameView.hideLoading()
            startGameView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: Game) {
            startGameView.onGameStarted(element.toModel())
        }
    }
}