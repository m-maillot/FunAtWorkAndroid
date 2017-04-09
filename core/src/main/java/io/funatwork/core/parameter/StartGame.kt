package io.funatwork.core.parameter

import io.funatwork.core.model.babyfoot.Team

/**
 * Created by mmaillot on 3/27/17.
 */
class StartGame {

    fun generateParameters(redTeam: Team, blueTeam: Team) =
            listOf(Pair("redPlayerAttackId", redTeam.attackPlayer.id),
                    Pair("redPlayerDefenseId", redTeam.defensePlayer.id),
                    Pair("bluePlayerAttackId", blueTeam.attackPlayer.id),
                    Pair("bluePlayerDefenseId", blueTeam.defensePlayer.id))
}