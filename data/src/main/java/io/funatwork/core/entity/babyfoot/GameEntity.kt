package io.funatwork.core.entity.babyfoot

import com.google.gson.annotations.SerializedName

data class GameEntity(val id: Int,
                      @SerializedName("started") val beginTimestampInSeconds: Long,
                      val redTeam: TeamEntity,
                      val blueTeam: TeamEntity,
                      val redTeamGoal: Int,
                      val blueTeamGoal: Int,
                      val goals: List<GoalEntity> = emptyList(),
                      val status: Int,
                      val ended: Long)