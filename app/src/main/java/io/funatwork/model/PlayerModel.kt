package io.funatwork.model

import java.io.Serializable

data class PlayerModel(val id: Int, val login: String, val name: String, val surname: String, val avatar: String) : Serializable