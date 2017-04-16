package io.funatwork.domain.exception


interface ErrorBundle {
    val exception: Exception

    val errorMessage: String
}