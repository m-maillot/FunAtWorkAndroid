package io.funatwork.core.repository.datasource.stats

import io.funatwork.core.entity.babyfoot.PlayerStatsEntity
import io.funatwork.core.entity.babyfoot.TeamStatsEntity
import io.funatwork.core.net.stats.StatsRestApi
import io.reactivex.Observable

class CloudStatsDataStore(val statsRestApi: StatsRestApi) : StatsDataStore {

    override fun playerStatsEntityList(): Observable<List<PlayerStatsEntity>> =
            statsRestApi.byPlayerEntity()

    override fun teamStatsEntityList(): Observable<List<TeamStatsEntity>> =
            statsRestApi.byTeamEntity()

    override fun playerStatsEntity(playerId: Int): Observable<PlayerStatsEntity> =
            statsRestApi.byPlayer(playerId)
}