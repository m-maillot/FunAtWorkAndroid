package io.funatwork.view

import io.funatwork.model.babyfoot.GameModel

interface GameView : LoadDataView {

    fun renderGame(game: GameModel)

    fun renderGoal(game: GameModel)

    fun renderGameFinished(game: GameModel)

}