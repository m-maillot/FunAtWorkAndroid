package io.funatwork.core.repository.datasource.tournament

import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.tournament.TournamentRestApiImpl

class TournamentDataStoreFactory(val connectionUtils: ConnectionUtils) {

    fun create() =
            createCloudDataStore()

    private fun createCloudDataStore() =
            CloudTournamentDataStore(TournamentRestApiImpl(connectionUtils))
}