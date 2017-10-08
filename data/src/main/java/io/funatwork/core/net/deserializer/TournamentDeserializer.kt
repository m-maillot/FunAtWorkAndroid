package io.funatwork.core.net.deserializer

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import io.funatwork.core.entity.babyfoot.TournamentEntity

class TournamentDeserializer : ResponseDeserializable<TournamentEntity> {

    override fun deserialize(content: String) = Gson().fromJson(content, TournamentEntity::class.java)
}