package io.funatwork.core.net

import io.funatwork.core.BuildConfig

object RestApiData {

    val API_BASE_URL = BuildConfig.BASE_URL

    val API_URL_GET_PLAYER_LIST = API_BASE_URL + "/api/v1/players"
    val API_URL_SIGNIN = API_BASE_URL + "/api/v1/signin"

    val API_URL_GET_GAME_LIST = API_BASE_URL + "/api/v1/babyfoot/games"
    val API_URL_CREATE_GAME_LIST = API_BASE_URL + "/api/v1/babyfoot/start"
    val API_URL_STOP_GAME_LIST = API_BASE_URL + "/api/v1/babyfoot/stop"
    val API_URL_ADD_GOAL = API_BASE_URL + "/api/v1/babyfoot/goal"
    val API_URL_STATS_PLAYER = API_BASE_URL + "/api/v1/babyfoot/stats/player"
    val API_URL_STATS_TEAM = API_BASE_URL + "/api/v1/babyfoot/stats/team"
    val API_URL_CURRENT_TOURNAMENT = API_BASE_URL + "/api/v1/babyfoot/tournament/current"
}