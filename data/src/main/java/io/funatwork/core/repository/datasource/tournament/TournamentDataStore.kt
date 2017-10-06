package io.funatwork.core.repository.datasource.tournament

import io.funatwork.core.entity.babyfoot.GameEntity
import io.funatwork.core.entity.babyfoot.TournamentEntity
import io.reactivex.Observable

/**
 * Contract with tournament API availability
 */
interface TournamentDataStore {

    fun currentTournament(): Observable<TournamentEntity>

    fun startGame(gameId: Int): Observable<GameEntity>
}