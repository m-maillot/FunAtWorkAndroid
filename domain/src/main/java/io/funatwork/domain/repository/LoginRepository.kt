package io.funatwork.domain.repository

import io.funatwork.domain.model.PlayerAuth
import io.reactivex.Observable

interface LoginRepository {

    fun signin(login: String, password: String): Observable<PlayerAuth>
}