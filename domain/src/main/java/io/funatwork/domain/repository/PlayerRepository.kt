package io.funatwork.domain.repository

import io.funatwork.domain.model.Player
import io.reactivex.Observable

interface PlayerRepository {
    fun players(): Observable<List<Player>>
}