package io.funatwork.core.repository.datasource.login

import com.github.kittinunf.fuel.core.FuelManager
import io.funatwork.core.cache.AccountCache

class CacheAccountDataStore(private val accountCache: AccountCache) : AccountStore {

    override fun signin(login: String, password: String) =
            throw NotImplementedError("Can't sign in from cache")

    override fun load() =
            accountCache.get().doOnNext { FuelManager.instance.baseHeaders = mapOf("Authorization" to it.token) }

}