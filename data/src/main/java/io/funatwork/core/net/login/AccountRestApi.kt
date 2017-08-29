package io.funatwork.core.net.login

import io.funatwork.core.entity.UserAuthEntity
import io.reactivex.Observable

interface AccountRestApi {

    fun login(login: String, password: String): Observable<UserAuthEntity>
}