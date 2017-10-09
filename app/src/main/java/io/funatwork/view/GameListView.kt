package io.funatwork.view

import io.funatwork.model.babyfoot.GameModel

interface GameListView {

    fun renderCurrentGame(game: GameModel)

    fun renderGameFinishedList(games: List<GameModel>)

    fun showLoadingCurrentGame()

    fun hideLoadingCurrentGame()

    fun showErrorCurrentGames(title: String, message: String)

    fun showLoadingGames()

    fun hideLoadingGames()

    fun showErrorGames(title: String, message: String)
}