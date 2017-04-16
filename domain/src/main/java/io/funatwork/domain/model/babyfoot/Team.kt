package io.funatwork.domain.model.babyfoot

import io.funatwork.domain.model.Player

data class Team(val id: Int = 0, val attackPlayer: Player, val defensePlayer: Player)