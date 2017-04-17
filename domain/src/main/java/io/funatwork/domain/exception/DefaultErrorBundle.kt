package io.funatwork.domain.exception

class DefaultErrorBundle(override val exception: Exception, override val errorMessage: String = "Unknown error") : ErrorBundle