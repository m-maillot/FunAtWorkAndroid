package io.funatwork.core

import android.content.Context
import com.github.kittinunf.fuse.core.Fuse

/**
 * Created by mmaillot on 3/26/17.
 */
class ApiClient(val context: Context) {

    init {
        Fuse.init(context.cacheDir.path)
    }

    /*
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
    */
}