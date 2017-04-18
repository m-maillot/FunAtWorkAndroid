package io.funatwork.model.babyfoot

import io.funatwork.model.PlayerModel
import java.io.Serializable

data class TeamModel(val id: Int = -1, val attackPlayer: PlayerModel, val defensePlayer: PlayerModel) : Serializable