package io.funatwork.model

import io.funatwork.domain.model.Player
import io.funatwork.domain.model.UserAuth

fun Player.toModel() =
        PlayerModel(id = id, name = name, surname = surname, avatar = avatar)

fun PlayerModel.toBo() =
        Player(id = id, name = name, surname = surname, avatar = avatar)

fun UserAuth.toModel() =
        UserAuthModel(player = player.toModel())