package io.funatwork.core.repository.datasource.game

import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.game.GameRestApiImpl
import io.funatwork.core.repository.datasource.game.CloudGameDataStore

class GameDataStoreFactory(val connectionUtils: ConnectionUtils) {

    fun create() =
            CloudGameDataStore(GameRestApiImpl(connectionUtils))
}