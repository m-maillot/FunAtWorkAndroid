package io.funatwork.core.net.deserializer

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import io.funatwork.core.entity.babyfoot.GameEntity

class GameDeserializer : ResponseDeserializable<GameEntity> {

    override fun deserialize(content: String): GameEntity = Gson().fromJson(content, GameEntity::class.java)
}