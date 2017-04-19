package io.funatwork.core.net

object RestApiData {

    val API_BASE_URL = "http://babyfoot.v3d.fr"
    // val API_BASE_URL = "http://192.168.1.68:8082"

    val API_URL_GET_PLAYER_LIST = API_BASE_URL + "/api/v1/players"

    val API_URL_GET_GAME_LIST = API_BASE_URL + "/api/v1/babyfoot/games"
    val API_URL_CREATE_GAME_LIST = API_BASE_URL + "/api/v1/babyfoot/start"
    val API_URL_STOP_GAME_LIST = API_BASE_URL + "/api/v1/babyfoot/stop"
    val API_URL_ADD_GOAL = API_BASE_URL + "/api/v1/babyfoot/goal"
}