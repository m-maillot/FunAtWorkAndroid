package io.funatwork.view

import io.funatwork.model.babyfoot.GameModel

interface StartGameView : LoadDataView {

    fun onGameStarted(game: GameModel)
}