package io.funatwork.core.entity.babyfoot

import io.funatwork.core.entity.PlayerEntity

data class GameEntity(val id: Int,
                      val creator: PlayerEntity,
                      val startedDate: String? = null,
                      val plannedDate: String? = null,
                      val redTeam: TeamEntity? = null,
                      val blueTeam: TeamEntity? = null,
                      val redTeamGoal: Int? = null,
                      val blueTeamGoal: Int? = null,
                      val goals: List<GoalEntity> = emptyList(),
                      val status: Int,
                      val endedDate: String? = null,
                      val tournamentId: Int? = null)