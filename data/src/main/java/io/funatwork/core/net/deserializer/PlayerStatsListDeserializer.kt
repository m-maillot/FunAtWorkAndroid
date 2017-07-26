package io.funatwork.core.net.deserializer

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.funatwork.core.entity.babyfoot.PlayerStatsEntity
import java.util.*

class PlayerStatsListDeserializer : ResponseDeserializable<List<PlayerStatsEntity>> {

    override fun deserialize(content: String): List<PlayerStatsEntity> =
            Gson().fromJson(content, object : TypeToken<ArrayList<PlayerStatsEntity>>() {}.type)
}