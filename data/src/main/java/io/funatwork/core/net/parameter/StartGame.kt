package io.funatwork.core.net.parameter

import io.funatwork.core.entity.babyfoot.TeamEntity

/**
 * Created by mmaillot on 3/27/17.
 */
class StartGame {

    fun generateParameters(pRedTeamEntity: TeamEntity, pBlueTeamEntity: TeamEntity) =
            listOf(Pair("redPlayerAttackId", pRedTeamEntity.pAttackPlayerEntity.id),
                    Pair("redPlayerDefenseId", pRedTeamEntity.pDefensePlayerEntity.id),
                    Pair("bluePlayerAttackId", pBlueTeamEntity.pAttackPlayerEntity.id),
                    Pair("bluePlayerDefenseId", pBlueTeamEntity.pDefensePlayerEntity.id))
}