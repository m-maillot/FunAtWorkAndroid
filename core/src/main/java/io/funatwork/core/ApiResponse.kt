package io.funatwork.core

/**
 * Created by mmaillot on 3/26/17.
 */
data class ApiResponse(val code: Int, val message: String) {

    fun hasFailed(): Boolean = code >= 400
}