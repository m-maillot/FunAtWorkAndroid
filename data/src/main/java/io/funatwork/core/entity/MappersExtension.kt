package io.funatwork.core.entity

import io.funatwork.domain.model.Player

fun PlayerEntity.toBo() =
        Player(id = id, name = name, avatar = avatar)

fun Player.toEntity() =
        PlayerEntity(id = id, name = name, avatar = avatar)