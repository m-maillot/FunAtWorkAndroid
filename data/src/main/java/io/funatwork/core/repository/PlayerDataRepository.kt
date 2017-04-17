package io.funatwork.core.repository

import io.funatwork.core.entity.toBo
import io.funatwork.core.repository.datasource.PlayerDataStoreFactory
import io.funatwork.domain.model.Player
import io.funatwork.domain.repository.PlayerRepository
import io.reactivex.Observable

class PlayerDataRepository(val playerDataStoreFactory: PlayerDataStoreFactory) : PlayerRepository {

    override fun players(): Observable<List<Player>> =
            playerDataStoreFactory.createCloudDataStore().playerEntityList().map { it.map { it.toBo() } }
}