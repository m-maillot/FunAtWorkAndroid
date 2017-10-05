package io.funatwork.core.net.tournament

import com.github.kittinunf.fuel.httpGet
import io.funatwork.core.entity.babyfoot.TournamentEntity
import io.funatwork.core.exception.NetworkConnectionException
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.RestApiData
import io.funatwork.core.net.deserializer.TournamentDeserializer
import io.reactivex.Observable

class TournamentRestApiImpl(val connectionUtils: ConnectionUtils) : TournamentRestApi {

    override fun currentTournament() =
            Observable.create<TournamentEntity> { emitter ->
                if (connectionUtils.isThereInternetConnection()) {
                    try {
                        val (_, _, result) = RestApiData.API_URL_CURRENT_TOURNAMENT.httpGet().responseObject(TournamentDeserializer())
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