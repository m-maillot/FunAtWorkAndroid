package io.funatwork.core.net.deserializer

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import io.funatwork.domain.model.UserAuthEntity

class UserAuthDeserializer : ResponseDeserializable<UserAuthEntity> {

    override fun deserialize(content: String): UserAuthEntity = Gson().fromJson(content, UserAuthEntity::class.java)
}