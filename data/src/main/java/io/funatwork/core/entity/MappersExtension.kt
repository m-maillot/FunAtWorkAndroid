package io.funatwork.core.entity

import io.funatwork.domain.model.Player
import io.funatwork.domain.model.UserAuth

fun PlayerEntity.toBo() =
        Player(id = id, login = login, name = name, surname = surname, avatar = avatar)

fun Player.toEntity() =
        PlayerEntity(id = id, login = login, name = name, surname = surname, avatar = avatar)

fun UserAuthEntity.toBo() =
        UserAuth(player = player.toBo(), token = token, expire_at = expire_at)