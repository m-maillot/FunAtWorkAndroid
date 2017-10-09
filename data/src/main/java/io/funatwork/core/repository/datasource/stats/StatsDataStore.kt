package io.funatwork.core.repository.datasource.stats

import io.funatwork.core.entity.babyfoot.PlayerStatsEntity
import io.funatwork.core.entity.babyfoot.TeamStatsEntity
import io.reactivex.Observable

interface StatsDataStore {

    fun playerStatsEntityList(): Observable<List<PlayerStatsEntity>>

    fun teamStatsEntityList(): Observable<List<TeamStatsEntity>>

    fun playerStatsEntity(playerId: Int): Observable<PlayerStatsEntity>

}