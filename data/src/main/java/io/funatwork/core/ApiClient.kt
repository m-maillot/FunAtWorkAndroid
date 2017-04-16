package io.funatwork.core

import android.content.Context
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuse.core.Fuse
import com.github.kittinunf.result.Result
import io.funatwork.core.entity.PlayerEntity
import io.funatwork.core.entity.babyfoot.GameEntity
import io.funatwork.core.entity.babyfoot.TeamEntity
import io.funatwork.core.net.deserializer.PlayerListDeserializer
import io.funatwork.core.net.parameter.AddGoal
import io.funatwork.core.net.parameter.StartGame

/**
 * Created by mmaillot on 3/26/17.
 */
class ApiClient(val context: Context) {

    init {
        Fuse.init(context.cacheDir.path)
    }

    fun getPlayers(handler: (ApiResponse, List<PlayerEntity>) -> Unit) {
        val players = "${Constants.baseUrl()}/api/v1/players"

        players.httpGet().responseObject(PlayerListDeserializer()) { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    handler(ApiResponse(response.httpStatusCode, response.data.toString()), emptyList())
                }
                is Result.Success -> {
                    handler(ApiResponse(response.httpStatusCode, ""), result.value)
                }
            }
        }
    }

    fun startGame(pRedTeamEntity: TeamEntity, pBlueTeamEntity: TeamEntity, handler: (ApiResponse, GameEntity?) -> Unit) {
        "${Constants.baseUrl()}/api/v1/babyfoot/start".httpPost(StartGame().generateParameters(pRedTeamEntity, pBlueTeamEntity)).responseObject(GameEntity.Deserializer()) { request, response, result ->
            Log.d("log", request.toString())
            Log.d("log", response.toString())
            when (result) {
                is Result.Failure -> {
                    handler(ApiResponse(response.httpStatusCode, response.httpResponseMessage), null)
                }
                is Result.Success -> {
                    handler(ApiResponse(response.httpStatusCode, ""), result.value)
                }
            }
        }
    }

    fun getGame(id: Int, handler: (ApiResponse, GameEntity?) -> Unit) {
        "${Constants.baseUrl()}/api/v1/babyfoot/games/$id".httpGet().responseObject(GameEntity.Deserializer()) { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    handler(ApiResponse(response.httpStatusCode, response.data.toString()), null)
                }
                is Result.Success -> {
                    handler(ApiResponse(response.httpStatusCode, ""), result.value)
                }
            }
        }
    }

    fun addGoal(pGameEntity: GameEntity, pStriker: PlayerEntity, handler: (ApiResponse, GameEntity?) -> Unit) {
        "${Constants.baseUrl()}/api/v1/babyfoot/goal".httpPost(AddGoal().generateParameters(pGameEntity, pStriker)).responseObject(GameEntity.Deserializer()) { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    handler(ApiResponse(response.httpStatusCode, response.data.toString()), null)
                }
                is Result.Success -> {
                    handler(ApiResponse(response.httpStatusCode, ""), result.value)
                }
            }
        }
    }
}