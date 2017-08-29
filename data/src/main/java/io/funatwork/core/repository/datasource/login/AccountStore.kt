package io.funatwork.core.repository.datasource.login

import io.funatwork.core.entity.UserAuthEntity
import io.reactivex.Observable

interface AccountStore {

    fun signin(login: String, password: String): Observable<UserAuthEntity>

    fun load(): Observable<UserAuthEntity>
}