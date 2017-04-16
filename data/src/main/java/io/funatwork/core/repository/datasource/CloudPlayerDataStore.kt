package io.funatwork.core.repository.datasource

import io.funatwork.core.cache.PlayerCache
import io.funatwork.core.net.PlayerRestApi

class CloudPlayerDataStore(val playerRestApi: PlayerRestApi, playerCache: PlayerCache) : PlayerDataStore {

    override fun playerEntityList() =
            playerRestApi.playerEntityList()
}