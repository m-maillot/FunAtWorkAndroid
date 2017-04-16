package io.funatwork.core.repository.datasource

import io.funatwork.core.cache.PlayerCache

class DiskPlayerDataStore(val playerCache: PlayerCache) : PlayerDataStore {

    override fun playerEntityList() =
            playerCache.get()
}