package io.funatwork.core.repository

import io.funatwork.core.entity.mapper.PlayerEntityMapper
import io.funatwork.core.repository.datasource.PlayerDataStoreFactory
import io.funatwork.domain.model.Player
import io.funatwork.domain.repository.PlayerRepository
import io.reactivex.Observable

class PlayerDataRepository(val playerDataStoreFactory: PlayerDataStoreFactory, val playerEntityMapper: PlayerEntityMapper) : PlayerRepository {

    override fun players(): Observable<List<Player>> =
            playerDataStoreFactory.createCloudDataStore().playerEntityList().map { playerEntityMapper.transform(it) }
}