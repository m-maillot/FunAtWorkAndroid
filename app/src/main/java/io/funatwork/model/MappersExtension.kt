package io.funatwork.model

import io.funatwork.domain.model.Player

fun Player.toModel() =
        PlayerModel(id = id, name = name, avatar = avatar)

fun PlayerModel.toBo() =
        Player(id = id, name = name, avatar = avatar)