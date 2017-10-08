package io.funatwork.core.repository

import io.funatwork.core.entity.babyfoot.toBo
import io.funatwork.core.repository.datasource.tournament.TournamentDataStoreFactory
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.model.babyfoot.Tournament
import io.funatwork.domain.repository.TournamentRepository
import io.reactivex.Observable

class TournamentDataRepository(val tournamentDataStoreFactory: TournamentDataStoreFactory) : TournamentRepository {

    override fun currentTournament(): Observable<Tournament> =
            tournamentDataStoreFactory.create().currentTournament().map { it.toBo() }

    override fun startGame(gameId: Int): Observable<Game> =
            tournamentDataStoreFactory.create().startGame(gameId).map { it.toBo() }

}