package io.funatwork.presenter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import io.funatwork.domain.interactor.AddGoal
import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.LoadGame
import io.funatwork.domain.interactor.StopGame
import io.funatwork.domain.interactor.params.AddGoalParam
import io.funatwork.domain.interactor.params.StopGameParam
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.model.babyfoot.GameStatus
import io.funatwork.gcm.PushMessageKeys
import io.funatwork.gcm.model.GameOverModel
import io.funatwork.gcm.model.NewGoalModel
import io.funatwork.model.PlayerModel
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.toBo
import io.funatwork.model.babyfoot.toModel
import io.funatwork.model.toBo
import io.funatwork.view.GameView
import java.util.concurrent.TimeUnit

class GamePresenter(private val gameView: GameView,
                    private val loadGame: LoadGame,
                    private val addGoal: AddGoal,
                    private val stopGame: StopGame) : Presenter {

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

    fun addGoal(game: GameModel, player: PlayerModel) {
        gameView.showNewGoalProcessing()
        addGoal.execute(GoalObserver(gameView), AddGoalParam(game.toBo(), player.toBo()))
    }

    fun addGamelle(game: GameModel, player: PlayerModel) {
        gameView.showNewGoalProcessing()
        addGoal.execute(GoalObserver(gameView), AddGoalParam(game.toBo(), player.toBo(), true))
    }

    fun timesUp(game: GameModel) {
        gameView.showNewGoalProcessing()
        stopGame.execute(TimesUpObserver(gameView), StopGameParam(game.id, false))
    }

    fun cancelGame() {
        stopGame.execute(CancelGameObserver(gameView), StopGameParam(mGameId, true))
    }

    private class GameObserver(val gameView: GameView) : DefaultObserver<Game>() {

        override fun onComplete() {
            gameView.hideLoading()
        }

        override fun onError(exception: Throwable?) {
            gameView.hideLoading()
            gameView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: Game) {
            gameView.renderGame(element.toModel())
        }
    }

    private class GoalObserver(val gameView: GameView) : DefaultObserver<Game>() {

        override fun onComplete() {
            gameView.dismissNewGoalProcessing()
        }

        override fun onError(exception: Throwable?) {
            gameView.dismissNewGoalProcessing()
            gameView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: Game) {
            val game = element.toModel()
            gameView.renderGoal(game.blueTeamGoal, game.redTeamGoal)
            if (game.status == GameStatus.OVER) {
                gameView.renderGameFinished(element.toModel())
            }
        }
    }

    private class CancelGameObserver(val gameView: GameView) : DefaultObserver<Game>() {

        override fun onComplete() {
            gameView.hideLoading()
        }

        override fun onError(exception: Throwable?) {
            gameView.hideLoading()
            gameView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: Game) {
            gameView.renderGameCanceled()
        }
    }

    private class TimesUpObserver(val gameView: GameView) : DefaultObserver<Game>() {

        override fun onComplete() {
            gameView.dismissNewGoalProcessing()
        }

        override fun onError(exception: Throwable?) {
            gameView.dismissNewGoalProcessing()
            gameView.showError(title = "Error happen", message = exception?.message ?: "Unknown Error")
        }

        override fun onNext(element: Game) {
            gameView.renderGameFinished(element.toModel())
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