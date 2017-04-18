package io.funatwork.model.babyfoot

import io.funatwork.model.PlayerModel
import java.io.Serializable

class GoalModel(val id: Int, val striker: PlayerModel, val position: Int) : Serializable