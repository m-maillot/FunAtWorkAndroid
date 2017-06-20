package io.funatwork.presenter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import io.funatwork.domain.interactor.AddGoal
import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.LoadGame
import io.funatwork.domain.interactor.StopGame
import io.funatwork.domain.interactor.params.AddGoalParam
import io.funatwork.domain.interactor.params.StopGameParam
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.gcm.PushMessageKeys
import io.funatwork.gcm.model.GameOverModel
import io.funatwork.gcm.model.NewGoalModel
import io.funatwork.model.PlayerModel
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.toBo
import io.funatwork.model.babyfoot.toModel
import io.funatwork.model.toBo
import io.funatwork.view.GameView

class GamePresenter(val gameView: GameView, val loadGame: LoadGame, val addGoal: AddGoal, val stopGame: StopGame) : Presenter {

    var mGameId = -1

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {}

    /**
     * Initializes the presenter by start retrieving the players list.
     */
    fun initialize(gameId: Int, context: Context) {
        mGameId = gameId
        loadGame(gameId)
        val filter = IntentFilter()
        filter.addAction(PushMessageKeys.ACTION_NEW_GOAL)
        filter.addAction(PushMessageKeys.ACTION_GAME_OVER)
        context.registerReceiver(PushMessageObserver(gameView), filter)
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

    fun addGamelle(game: GameModel, player: PlayerModel) =
            addGoal.execute(GoalObserver(gameView), AddGoalParam(game.toBo(), player.toBo(), true))


    fun cancelGame() {
        stopGame.execute(CancelGameObserver(gameView), StopGameParam(mGameId, true))
    }

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
            gameView.renderGoal(game.blueTeamGoal, game.redTeamGoal)
            if (game.status == game.GAME_OVER) {
                gameView.renderGameFinished(game.toModel())
            }
        }
    }

    private class CancelGameObserver(val gameView: GameView) : DefaultObserver<Game>() {

        override fun onComplete() {
            gameView.hideLoading()
        }

        override fun onError(e: Throwable?) {
            gameView.hideLoading()
            gameView.showError(title = "Error happen", message = e?.message ?: "Unknown Error")
        }

        override fun onNext(game: Game) {
            gameView.renderGameCanceled()
        }
    }

    private class PushMessageObserver(val gameView: GameView) : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                if (intent.action == PushMessageKeys.ACTION_NEW_GOAL) {
                    val data = intent.getParcelableExtra<NewGoalModel>(PushMessageKeys.EXTRA_DATA)
                    gameView.renderGoal(data.blueScore, data.redScore)
                } else if (intent.action == PushMessageKeys.ACTION_GAME_OVER) {
                    val data = intent.getParcelableExtra<GameOverModel>(PushMessageKeys.EXTRA_DATA)
                    // gameView.renderGameFinished(
                }
            }
        }

    }
}