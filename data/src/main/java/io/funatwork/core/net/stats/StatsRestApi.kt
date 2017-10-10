package io.funatwork.core.net.stats

import io.funatwork.core.entity.babyfoot.PlayerStatsEntity
import io.funatwork.core.entity.babyfoot.TeamStatsEntity
import io.reactivex.Observable

interface StatsRestApi {

    fun byPlayerEntity(): Observable<List<PlayerStatsEntity>>

    fun byTeamEntity(): Observable<List<TeamStatsEntity>>

    fun byPlayer(playerId: Int): Observable<PlayerStatsEntity>
}