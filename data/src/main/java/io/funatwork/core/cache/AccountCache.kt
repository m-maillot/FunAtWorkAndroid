package io.funatwork.core.cache

import io.funatwork.core.entity.UserAuthEntity
import io.reactivex.Observable

/**
 * Created by mmaillot on 7/27/17.
 */
interface AccountCache {

    fun put(userAuthEntity: UserAuthEntity)

    fun get(): Observable<UserAuthEntity>

    fun isExpired(): Boolean

    fun evict()
}