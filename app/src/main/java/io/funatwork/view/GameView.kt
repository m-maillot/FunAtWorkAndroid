package io.funatwork.view

import io.funatwork.model.babyfoot.GameModel

interface GameView : LoadDataView {

    fun renderGame(game: GameModel)

    fun renderGoal(blueScore: Int, redScore: Int)

    fun renderGameFinished(game: GameModel)

    fun renderGameCanceled()

    fun showNewGoalProcessing()

    fun dismissNewGoalProcessing()

}