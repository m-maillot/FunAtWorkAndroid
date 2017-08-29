package io.funatwork.core.repository.datasource.login

import io.funatwork.core.cache.AccountCache
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.login.AccountRestApiImpl

class AccountDataStoreFactory(val connectionUtils: ConnectionUtils, val accountCache: AccountCache) {

    fun createCacheDataStore() =
            CacheAccountDataStore(accountCache)

    fun createCloudDataStore() =
            CloudAccountDataStore(AccountRestApiImpl(connectionUtils), accountCache)
}