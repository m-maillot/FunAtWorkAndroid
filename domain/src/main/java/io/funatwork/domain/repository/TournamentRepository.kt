package io.funatwork.domain.repository

import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.model.babyfoot.Tournament
import io.reactivex.Observable

interface TournamentRepository {

    fun currentTournament(): Observable<Tournament>

    fun startGame(gameId: Int): Observable<Game>

}