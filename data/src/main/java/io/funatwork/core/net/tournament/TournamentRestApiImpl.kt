package io.funatwork.core.net.tournament

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import io.funatwork.core.entity.babyfoot.GameEntity
import io.funatwork.core.entity.babyfoot.TournamentEntity
import io.funatwork.core.exception.NetworkConnectionException
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.RestApiData
import io.funatwork.core.net.deserializer.GameDeserializer
import io.funatwork.core.net.deserializer.TournamentDeserializer
import io.reactivex.Observable

class TournamentRestApiImpl(val connectionUtils: ConnectionUtils) : TournamentRestApi {


    override fun currentTournament(): Observable<TournamentEntity> =
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

    override fun startGame(gameId: Int): Observable<GameEntity> =
            Observable.create<GameEntity> { emitter ->
                if (connectionUtils.isThereInternetConnection()) {
                    try {
                        val (_, _, result) = RestApiData.API_URL_START_TOURNAMENT_GAME.httpPost(generateStartGameParameters(gameId)).responseObject(GameDeserializer())
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

    private fun generateStartGameParameters(gameId: Int) =
            listOf(Pair("gameId", gameId))
}