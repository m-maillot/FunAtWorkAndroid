package io.funatwork.core.model.babyfoot

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

/**
 * Created by mmaillot on 3/26/17.
 */
data class Game(val id: Int,
                @SerializedName("started") val beginTimestampInSeconds: Long,
                val redTeam: Team,
                val blueTeam: Team,
                val redTeamGoal: Int,
                val blueTeamGoal: Int,
                val goals: List<Goal>,
                val status: Int,
                val ended: Long) {

    class Deserializer : ResponseDeserializable<Game> {

        override fun deserialize(content: String): Game = Gson().fromJson(content, Game::class.java)
    }
}