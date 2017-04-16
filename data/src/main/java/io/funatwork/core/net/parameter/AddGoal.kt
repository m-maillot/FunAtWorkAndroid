package io.funatwork.core.net.parameter

import io.funatwork.core.entity.PlayerEntity
import io.funatwork.core.entity.babyfoot.GameEntity

/**
 * Created by mmaillot on 3/27/17.
 */
class AddGoal {

    fun generateParameters(pGameEntity: GameEntity, pStriker: PlayerEntity) =
            listOf(Pair("pGameEntity", pGameEntity.id),
                    Pair("pStriker", pStriker.id),
                    Pair("position", 1))
}