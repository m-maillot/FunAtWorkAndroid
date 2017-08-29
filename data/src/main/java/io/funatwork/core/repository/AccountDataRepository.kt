package io.funatwork.core.repository

import io.funatwork.core.entity.toBo
import io.funatwork.core.repository.datasource.login.AccountDataStoreFactory
import io.funatwork.domain.repository.AccountRepository

class AccountDataRepository(private val accountDataStoreFactory: AccountDataStoreFactory) : AccountRepository {

    override fun load() =
            accountDataStoreFactory.createCacheDataStore().load().map { it.toBo() }

    override fun signin(login: String, password: String) =
            accountDataStoreFactory.createCloudDataStore().signin(login, password).map { it.toBo() }
}