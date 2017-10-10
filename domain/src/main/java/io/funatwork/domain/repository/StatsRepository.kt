package io.funatwork.domain.repository

import io.funatwork.domain.model.babyfoot.PlayerStats
import io.funatwork.domain.model.babyfoot.TeamStats
import io.reactivex.Observable

interface StatsRepository {
    fun byPlayers(): Observable<List<PlayerStats>>

    fun byTeams(): Observable<List<TeamStats>>

    fun byPlayer(playerId: Int): Observable<PlayerStats>
}