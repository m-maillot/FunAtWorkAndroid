package io.funatwork.core.entity.babyfoot

import io.funatwork.core.entity.PlayerEntity

data class TeamEntity(val id: Int = 0, val attackPlayer: PlayerEntity, val defensePlayer: PlayerEntity)