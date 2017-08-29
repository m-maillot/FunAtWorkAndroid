package io.funatwork.domain.repository

import io.funatwork.domain.model.UserAuth
import io.reactivex.Observable

interface AccountRepository {

    fun signin(login: String, password: String): Observable<UserAuth>

    fun load() : Observable<UserAuth>
}