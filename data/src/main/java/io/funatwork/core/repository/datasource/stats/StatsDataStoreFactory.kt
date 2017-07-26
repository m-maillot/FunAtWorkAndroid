package io.funatwork.core.repository.datasource.stats

import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.stats.StatsRestApiImpl


/**
 * Created by mmaillot on 4/14/17.
 */
class StatsDataStoreFactory(val connectionUtils: ConnectionUtils) {

    fun create() =
            createCloudDataStore()

    fun createCloudDataStore() =
            CloudStatsDataStore(StatsRestApiImpl(connectionUtils))

}