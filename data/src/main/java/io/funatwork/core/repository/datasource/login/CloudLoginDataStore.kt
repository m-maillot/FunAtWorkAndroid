package io.funatwork.core.repository.datasource.login

import io.funatwork.core.net.game.GameRestApi
import io.funatwork.domain.model.UserAuthEntity
import io.reactivex.Observable

class CloudLoginDataStore(val gameRestApi: GameRestApi) : LoginStore {

    override fun signin(login: String, password: String): Observable<UserAuthEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}