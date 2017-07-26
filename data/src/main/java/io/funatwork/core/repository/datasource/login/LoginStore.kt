package io.funatwork.core.repository.datasource.login

import io.funatwork.domain.model.UserAuthEntity
import io.reactivex.Observable

interface LoginStore {

    fun signin(login: String, password: String): Observable<UserAuthEntity>
}