package io.funatwork.core.net.deserializer

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.funatwork.core.entity.babyfoot.GameEntity
import java.util.*

class GameListDeserializer : ResponseDeserializable<List<GameEntity>> {
    override fun deserialize(content: String): List<GameEntity> =
            Gson().fromJson(content, object : TypeToken<ArrayList<GameEntity>>() {}.type)
}