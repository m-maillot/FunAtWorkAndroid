package io.funatwork.domain.model

data class PlayerAuth(val login: String, val token: String, val expire_at: Long)