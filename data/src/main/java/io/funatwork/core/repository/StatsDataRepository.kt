package io.funatwork.core.repository

import io.funatwork.core.entity.babyfoot.toBo
import io.funatwork.core.repository.datasource.stats.StatsDataStoreFactory
import io.funatwork.domain.model.babyfoot.PlayerStats
import io.funatwork.domain.model.babyfoot.TeamStats
import io.funatwork.domain.repository.StatsRepository
import io.reactivex.Observable

class StatsDataRepository(val statsDataStoreFactory: StatsDataStoreFactory) : StatsRepository {
    override fun byPlayers(): Observable<List<PlayerStats>> =
            statsDataStoreFactory.createCloudDataStore().playerStatsEntityList().map { it.map { it.toBo() } }

    override fun byTeams(): Observable<List<TeamStats>> =
            statsDataStoreFactory.createCloudDataStore().teamStatsEntityList().map { it.map { it.toBo() } }

}