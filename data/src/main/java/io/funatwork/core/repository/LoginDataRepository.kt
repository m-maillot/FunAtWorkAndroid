package io.funatwork.core.repository

import io.funatwork.core.entity.toBo
import io.funatwork.core.repository.datasource.login.LoginDataStoreFactory
import io.funatwork.domain.repository.LoginRepository

class LoginDataRepository(val loginDataStoreFactory: LoginDataStoreFactory) : LoginRepository {

    override fun signin(login: String, password: String) =
            loginDataStoreFactory.create().signin(login, password).map { it.toBo() }
}