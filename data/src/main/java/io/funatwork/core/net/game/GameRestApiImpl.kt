package io.funatwork.core.net.game

import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import io.funatwork.core.entity.PlayerEntity
import io.funatwork.core.entity.babyfoot.GameEntity
import io.funatwork.core.entity.babyfoot.TeamEntity
import io.funatwork.core.exception.NetworkConnectionException
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.RestApiData
import io.funatwork.core.net.deserializer.GameDeserializer
import io.funatwork.core.net.deserializer.GameListDeserializer
import io.funatwork.core.net.game.GameRestApi
import io.reactivex.Observable

class GameRestApiImpl(val connectionUtils: ConnectionUtils) : GameRestApi {

    override fun gameEntity(gameId: Int): Observable<GameEntity> =
            Observable.create<GameEntity> { emitter ->
                if (connectionUtils.isThereInternetConnection()) {
                    try {
                        val (request, response, result) = "${RestApiData.API_URL_GET_GAME_LIST}/$gameId".httpGet().responseObject(GameDeserializer())
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

    override fun startGame(redTeam: TeamEntity, blueTeam: TeamEntity): Observable<GameEntity> =
            Observable.create<GameEntity> { emitter ->
                if (connectionUtils.isThereInternetConnection()) {
                    try {
                        val (request, response, result) = RestApiData.API_URL_CREATE_GAME_LIST.httpPost(generateParameters(redTeam = redTeam, blueTeam = blueTeam)).responseObject(GameDeserializer())
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

    override fun gameEntityList(): Observable<List<GameEntity>> =
            Observable.create<List<GameEntity>> { emitter ->
                if (connectionUtils.isThereInternetConnection()) {
                    try {
                        val (request, response, result) = RestApiData.API_URL_GET_GAME_LIST.httpGet().responseObject(GameListDeserializer())
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

    override fun addGoal(gameId: Int, striker: PlayerEntity): Observable<GameEntity> =
            Observable.create<GameEntity> { emitter ->
                if (connectionUtils.isThereInternetConnection()) {
                    try {
                        val (request, response, result) = RestApiData.API_URL_ADD_GOAL.httpPost(generateParameters(gameId = gameId, striker = striker)).responseObject(GameDeserializer())
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

    override fun stopGame(gameId: Int, cancelled: Boolean): Observable<GameEntity> =
            Observable.create<GameEntity> { emitter ->
                if (connectionUtils.isThereInternetConnection()) {
                    try {
                        val (request, response, result) = RestApiData.API_URL_STOP_GAME_LIST.httpPost(generateStopParameters(gameId, cancelled)).responseObject(GameDeserializer())
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

    private fun generateParameters(redTeam: TeamEntity, blueTeam: TeamEntity) =
            listOf(Pair("redPlayerAttackId", redTeam.attackPlayer.id),
                    Pair("redPlayerDefenseId", redTeam.defensePlayer.id),
                    Pair("bluePlayerAttackId", blueTeam.attackPlayer.id),
                    Pair("bluePlayerDefenseId", blueTeam.defensePlayer.id))

    private fun generateParameters(gameId: Int, striker: PlayerEntity) =
            listOf(Pair("game", gameId),
                    Pair("striker", striker.id),
                    Pair("position", 1))

    private fun generateStopParameters(gameId: Int, cancelled: Boolean) =
            listOf(Pair("game", gameId),
                    Pair("cancelled", if (cancelled) 1 else 0))
}