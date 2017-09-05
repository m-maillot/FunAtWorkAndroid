package io.funatwork.core.net.player

import com.github.kittinunf.fuel.httpGet
import io.funatwork.core.entity.PlayerEntity
import io.funatwork.core.entity.UserAuthEntity
import io.funatwork.core.exception.NetworkConnectionException
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.RestApiData
import io.funatwork.core.net.deserializer.PlayerListDeserializer
import io.reactivex.Observable

class PlayerRestApiImpl(val connectionUtils: ConnectionUtils) : PlayerRestApi {

    override fun playerEntityList(): Observable<List<PlayerEntity>> =
            Observable.create<List<PlayerEntity>> { emitter ->
                if (connectionUtils.isThereInternetConnection()) {
                    try {
                        val (_, _, result) = RestApiData.API_URL_GET_PLAYER_LIST.httpGet().responseObject(PlayerListDeserializer())
                        if (result.component2() == null) {
                            emitter.onNext(result.get())
                            emitter.onComplete()
                        } else {
                            emitter.onError(NetworkConnectionException(result.component2()))
                        }
                    } catch (e: Exception) {
                        emitter.onError(NetworkConnectionException(e.cause))
                    }
                } else {
                    emitter.onError(NetworkConnectionException())
                }
            }
}