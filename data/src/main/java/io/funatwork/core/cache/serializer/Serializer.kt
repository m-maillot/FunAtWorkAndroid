package io.funatwork.core.cache.serializer

import com.google.gson.Gson
import java.lang.reflect.Type

class Serializer<T> {

    val gson = Gson()

    /**
     * Serialize an object to Json.
     *
     * @param object to serialize.
     */
    fun serialize(obj: Any, type: Type) =
            gson.toJson(obj, type)

    /**
     * Deserialize a json representation of an object.
     *
     * @param string A json string to deserialize.
     */
    fun deserialize(data: String, type: Type): T? =
            gson.fromJson(data, type)
}