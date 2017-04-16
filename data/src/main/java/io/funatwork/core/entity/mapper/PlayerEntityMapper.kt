package io.funatwork.core.entity.mapper

import io.funatwork.core.entity.PlayerEntity
import io.funatwork.domain.model.Player

class PlayerEntityMapper {

    fun transform(playersEntity: List<PlayerEntity>) =
            playersEntity.map { transform(it) }

    fun transform(playerEntity: PlayerEntity) =
            Player(id = playerEntity.id, name = playerEntity.name, avatar = playerEntity.avatar)
}