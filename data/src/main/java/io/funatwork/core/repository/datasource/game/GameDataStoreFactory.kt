package io.funatwork.core.repository.datasource

import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.player.GameRestApiImpl

class GameDataStoreFactory(val connectionUtils: ConnectionUtils) {

    fun create() =
            CloudGameDataStore(GameRestApiImpl(connectionUtils))
}