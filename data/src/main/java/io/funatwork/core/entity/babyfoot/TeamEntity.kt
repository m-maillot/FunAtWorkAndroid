package io.funatwork.core.entity.babyfoot

import io.funatwork.core.entity.PlayerEntity

/**
 * Created by mmaillot on 3/26/17.
 */
data class TeamEntity(val id: Int = 0, val pAttackPlayerEntity: PlayerEntity, val pDefensePlayerEntity: PlayerEntity)