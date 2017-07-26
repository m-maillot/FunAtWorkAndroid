package io.funatwork.core.net.login

import io.funatwork.domain.model.UserAuthEntity
import io.reactivex.Observable

interface LoginRestApi {

    fun login(login: String, password: String): Observable<UserAuthEntity>
}