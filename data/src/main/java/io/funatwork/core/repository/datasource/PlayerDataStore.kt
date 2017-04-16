package io.funatwork.core.repository.datasource

import io.funatwork.core.entity.PlayerEntity
import io.reactivex.Observable

interface PlayerDataStore {
    fun playerEntityList(): Observable<List<PlayerEntity>>
}