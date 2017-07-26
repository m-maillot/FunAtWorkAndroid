package io.funatwork.core.repository.datasource.login

import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.game.GameRestApiImpl
import io.funatwork.core.repository.datasource.game.CloudGameDataStore

class LoginDataStoreFactory(val connectionUtils: ConnectionUtils) {

    fun create() =
            CloudLoginDataStore(GameRestApiImpl(connectionUtils))
}