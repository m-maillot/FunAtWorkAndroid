package io.funatwork.core.entity

data class UserAuthEntity(val player: PlayerEntity, val token: String, val expire_at: Long)