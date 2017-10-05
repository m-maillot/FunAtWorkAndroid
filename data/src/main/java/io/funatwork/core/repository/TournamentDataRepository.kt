package io.funatwork.core.repository

import io.funatwork.core.entity.babyfoot.toBo
import io.funatwork.core.repository.datasource.tournament.TournamentDataStoreFactory
import io.funatwork.domain.repository.TournamentRepository

class TournamentDataRepository(val tournamentDataStoreFactory: TournamentDataStoreFactory) : TournamentRepository {

    override fun currentTournament() =
            tournamentDataStoreFactory.create().currentTournament().map { it.toBo() }

}