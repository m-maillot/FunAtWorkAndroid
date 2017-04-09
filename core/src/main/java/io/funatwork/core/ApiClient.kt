package io.funatwork.core

import android.content.Context
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuse.core.Fuse
import com.github.kittinunf.result.Result
import io.funatwork.core.model.Player
import io.funatwork.core.model.babyfoot.Game
import io.funatwork.core.model.babyfoot.Team
import io.funatwork.core.parameter.AddGoal
import io.funatwork.core.parameter.StartGame

/**
 * Created by mmaillot on 3/26/17.
 */
class ApiClient(val context: Context) {

    init {
        Fuse.init(context.cacheDir.path)
    }

    fun getPlayers(handler: (ApiResponse, List<Player>) -> Unit) {
        val players = "${Constants.baseUrl()}/api/v1/players"

        players.httpGet().responseObject(Player.ListDeserializer()) { request, response, result ->
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

    fun startGame(redTeam: Team, blueTeam: Team, handler: (ApiResponse, Game?) -> Unit) {
        "${Constants.baseUrl()}/api/v1/babyfoot/start".httpPost(StartGame().generateParameters(redTeam, blueTeam)).responseObject(Game.Deserializer()) { request, response, result ->
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

    fun getGame(id: Int, handler: (ApiResponse, Game?) -> Unit) {
        "${Constants.baseUrl()}/api/v1/babyfoot/games/$id".httpGet().responseObject(Game.Deserializer()) { request, response, result ->
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

    fun addGoal(game: Game, striker: Player, handler: (ApiResponse, Game?) -> Unit) {
        "${Constants.baseUrl()}/api/v1/babyfoot/goal".httpPost(AddGoal().generateParameters(game, striker)).responseObject(Game.Deserializer()) { request, response, result ->
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