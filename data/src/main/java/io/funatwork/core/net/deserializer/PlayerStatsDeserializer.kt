package io.funatwork.core.net.deserializer

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.funatwork.core.entity.babyfoot.PlayerStatsEntity

class PlayerStatsDeserializer : ResponseDeserializable<PlayerStatsEntity> {

    override fun deserialize(content: String): PlayerStatsEntity =
            Gson().fromJson(content, object : TypeToken<PlayerStatsEntity>() {}.type)
}