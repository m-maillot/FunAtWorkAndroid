package io.funatwork.core.entity

import io.funatwork.domain.model.Player
import io.funatwork.domain.model.PlayerAuth
import io.funatwork.domain.model.UserAuthEntity

fun PlayerEntity.toBo() =
        Player(id = id, name = name, avatar = avatar)

fun Player.toEntity() =
        PlayerEntity(id = id, name = name, avatar = avatar)

fun UserAuthEntity.toBo() =
        PlayerAuth(login = login, token = token, expire_at = expire_at)