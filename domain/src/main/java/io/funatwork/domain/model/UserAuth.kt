package io.funatwork.domain.model

data class UserAuth(val player: Player, val token: String, val expire_at: Long)