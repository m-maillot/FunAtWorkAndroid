package io.funatwork.core.net.stats

import com.github.kittinunf.fuel.httpGet
import io.funatwork.core.entity.babyfoot.PlayerStatsEntity
import io.funatwork.core.entity.babyfoot.TeamStatsEntity
import io.funatwork.core.exception.NetworkConnectionException
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.RestApiData
import io.funatwork.core.net.deserializer.PlayerStatsDeserializer
import io.funatwork.core.net.deserializer.PlayerStatsListDeserializer
import io.funatwork.core.net.deserializer.TeamStatsListDeserializer
import io.reactivex.Observable

class StatsRestApiImpl(val connectionUtils: ConnectionUtils) : StatsRestApi {

    override fun byPlayerEntity(): Observable<List<PlayerStatsEntity>> =
            Observable.create<List<PlayerStatsEntity>> { emitter ->
                if (connectionUtils.isThereInternetConnection()) {
                    try {
                        val (_, _, result) = RestApiData.API_URL_STATS_PLAYER.httpGet().responseObject(PlayerStatsListDeserializer())
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

    override fun byTeamEntity(): Observable<List<TeamStatsEntity>> =
            Observable.create<List<TeamStatsEntity>> { emitter ->
                if (connectionUtils.isThereInternetConnection()) {
                    try {
                        val (_, _, result) = RestApiData.API_URL_STATS_TEAM.httpGet().responseObject(TeamStatsListDeserializer())
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

    override fun byPlayer(playerId: Int): Observable<PlayerStatsEntity> =
            Observable.create<PlayerStatsEntity> { emitter ->
                if (connectionUtils.isThereInternetConnection()) {
                    try {
                        val (_, _, result) = "${RestApiData.API_URL_STATS_TEAM}/$playerId".httpGet().responseObject(PlayerStatsDeserializer())
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