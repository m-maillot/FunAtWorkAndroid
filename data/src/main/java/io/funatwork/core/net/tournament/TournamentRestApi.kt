package io.funatwork.core.net.tournament

import io.funatwork.core.entity.babyfoot.GameEntity
import io.funatwork.core.entity.babyfoot.TournamentEntity
import io.reactivex.Observable

interface TournamentRestApi {

    fun currentTournament(): Observable<TournamentEntity>

    fun startGame(gameId: Int): Observable<GameEntity>
}