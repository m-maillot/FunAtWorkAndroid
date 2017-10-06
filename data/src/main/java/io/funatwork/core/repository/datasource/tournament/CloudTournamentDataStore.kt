package io.funatwork.core.repository.datasource.tournament

import io.funatwork.core.net.tournament.TournamentRestApi

class CloudTournamentDataStore(private val tournamentRestApi: TournamentRestApi) : TournamentDataStore {

    override fun currentTournament() =
            tournamentRestApi.currentTournament()

    override fun startGame(gameId: Int) =
            tournamentRestApi.startGame(gameId)
}