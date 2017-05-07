package io.funatwork.view

import io.funatwork.model.babyfoot.GameModel

interface GameListView : LoadDataView {

    fun renderCurrentGame(game: GameModel?)


    fun renderGameFinishedList(games: List<GameModel>)
}