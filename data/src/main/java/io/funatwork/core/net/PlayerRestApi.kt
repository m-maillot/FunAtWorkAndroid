package io.funatwork.core.net

import io.funatwork.core.entity.PlayerEntity
import io.reactivex.Observable

interface PlayerRestApi {

    fun playerEntityList(): Observable<List<PlayerEntity>>
}