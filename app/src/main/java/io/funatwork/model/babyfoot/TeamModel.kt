package io.funatwork.model.babyfoot

import io.funatwork.model.PlayerModel

data class TeamModel(val id: Int = -1, val attackPlayer: PlayerModel, val defensePlayer: PlayerModel)