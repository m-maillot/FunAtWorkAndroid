package io.funatwork.view

import io.funatwork.model.babyfoot.TournamentModel

interface TournamentView : LoadDataView {

    fun renderCurrentTournament(tournament: TournamentModel)
}