package io.funatwork.core.entity.babyfoot

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

/**
 * Created by mmaillot on 3/26/17.
 */
data class GameEntity(val id: Int,
                      @SerializedName("started") val beginTimestampInSeconds: Long,
                      val pRedTeamEntity: TeamEntity,
                      val pBlueTeamEntity: TeamEntity,
                      val redTeamGoal: Int,
                      val blueTeamGoal: Int,
                      val pGoalEntities: List<GoalEntity>,
                      val status: Int,
                      val ended: Long) {

    class Deserializer : ResponseDeserializable<GameEntity> {

        override fun deserialize(content: String): GameEntity = Gson().fromJson(content, GameEntity::class.java)
    }
}