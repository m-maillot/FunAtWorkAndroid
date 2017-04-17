package io.funatwork.domain.interactor.params

import io.funatwork.domain.model.Player
import io.funatwork.domain.model.babyfoot.Game

data class AddGoalParam(val game: Game, val stricker: Player)