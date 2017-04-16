package io.funatwork.core.net.deserializer

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.funatwork.core.entity.PlayerEntity
import java.util.*

class PlayerListDeserializer : ResponseDeserializable<List<PlayerEntity>> {
    override fun deserialize(content: String): List<PlayerEntity> =
            Gson().fromJson(content, object : TypeToken<ArrayList<PlayerEntity>>() {}.type)
}