package io.funatwork.core.net.parameter

/**
 * Created by mmaillot on 3/27/17.
 */
class GetGame {

    fun generateParameters(gameId: Int) =
            listOf(Pair("game", gameId))
}