package io.funatwork.core.cache

import io.funatwork.core.entity.PlayerEntity
import io.reactivex.Observable

interface PlayerCache {

    fun get(): Observable<List<PlayerEntity>>

    fun put(players: List<PlayerEntity>)

    fun isExpired(): Boolean

    fun evict()
}