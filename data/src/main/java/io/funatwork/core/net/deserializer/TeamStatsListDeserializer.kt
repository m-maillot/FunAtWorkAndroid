package io.funatwork.core.net.deserializer

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.funatwork.core.entity.babyfoot.TeamStatsEntity
import java.util.*

class TeamStatsListDeserializer : ResponseDeserializable<List<TeamStatsEntity>> {

    override fun deserialize(content: String): List<TeamStatsEntity> =
            Gson().fromJson(content, object : TypeToken<ArrayList<TeamStatsEntity>>() {}.type)
}