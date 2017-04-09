package io.funatwork.core.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by mmaillot on 3/26/17.
 */
data class Player(val id: Int, val name: String, val avatar: String) {

    class Deserializer : ResponseDeserializable<Player> {

        override fun deserialize(content: String): Player = Gson().fromJson(content, Player::class.java)
    }

    class ListDeserializer : ResponseDeserializable<List<Player>> {

        override fun deserialize(content: String): List<Player> {
            val listType = object : TypeToken<ArrayList<Player>>() {}.type
            return Gson().fromJson(content, listType)
        }
    }
}