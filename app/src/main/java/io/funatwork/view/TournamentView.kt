package io.funatwork.view

import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.TournamentModel

interface TournamentView : LoadDataView {

    fun renderCurrentTournament(tournament: TournamentModel)

    fun startGame(game: GameModel)

    fun showLoadingTournament()

    fun showErrorLoadingTournament()
}