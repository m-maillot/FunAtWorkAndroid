package io.funatwork.core.repository.datasource.tournament

import io.funatwork.core.net.tournament.TournamentRestApi

class CloudTournamentDataStore(val tournamentRestApi: TournamentRestApi) : TournamentDataStore {

    override fun currentTournament() =
            tournamentRestApi.currentTournament()

}