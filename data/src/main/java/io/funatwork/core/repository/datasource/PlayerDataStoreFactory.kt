package io.funatwork.core.repository.datasource

import io.funatwork.core.cache.PlayerCache
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.PlayerRestApiImpl


/**
 * Created by mmaillot on 4/14/17.
 */
class PlayerDataStoreFactory(val connectionUtils: ConnectionUtils, val playerCache: PlayerCache) {

    fun create() =
            if (!playerCache.isExpired()) DiskPlayerDataStore(playerCache) else createCloudDataStore()

    fun createCloudDataStore() =
            CloudPlayerDataStore(PlayerRestApiImpl(connectionUtils), playerCache)

}