package io.funatwork.core.repository.datasource.stats

import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.stats.StatsRestApiImpl

class StatsDataStoreFactory(val connectionUtils: ConnectionUtils) {

    fun create() =
            createCloudDataStore()

    fun createCloudDataStore() =
            CloudStatsDataStore(StatsRestApiImpl(connectionUtils))

}