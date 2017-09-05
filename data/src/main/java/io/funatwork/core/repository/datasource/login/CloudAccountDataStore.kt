package io.funatwork.core.repository.datasource.login

import com.github.kittinunf.fuel.core.FuelManager
import io.funatwork.core.cache.AccountCache
import io.funatwork.core.net.login.AccountRestApi

class CloudAccountDataStore(private val accountRestApi: AccountRestApi,
                            private val accountCache: AccountCache) : AccountStore {

    override fun signin(login: String, password: String) =
            accountRestApi.login(login, password).doOnNext {
                accountCache.put(it)
                FuelManager.instance.baseHeaders = mapOf("Authorization" to it.token)
            }

    override fun load() =
            throw NotImplementedError("")

}