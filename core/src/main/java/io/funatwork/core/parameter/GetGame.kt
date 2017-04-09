package io.funatwork.core.parameter

/**
 * Created by mmaillot on 3/27/17.
 */
class GetGame {

    fun generateParameters(gameId: Int) =
            listOf(Pair("game", gameId))
}