package io.funatwork.core.parameter

import io.funatwork.core.model.Player
import io.funatwork.core.model.babyfoot.Game

/**
 * Created by mmaillot on 3/27/17.
 */
class AddGoal {

    fun generateParameters(game: Game, striker: Player) =
            listOf(Pair("game", game.id),
                    Pair("striker", striker.id),
                    Pair("position", 1))
}