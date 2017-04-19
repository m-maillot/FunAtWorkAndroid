package io.funatwork.core.net.parameter

class GetGame {

    fun generateParameters(gameId: Int) =
            listOf(Pair("game", gameId))
}