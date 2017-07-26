package io.funatwork.domain.model

data class UserAuthEntity(val login: String, val token: String, val expire_at: Long)