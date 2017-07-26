package io.funatwork.core.net.login

import com.github.kittinunf.fuel.httpPost
import io.funatwork.core.exception.NetworkConnectionException
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.net.RestApiData
import io.funatwork.core.net.deserializer.UserAuthDeserializer
import io.funatwork.domain.model.UserAuthEntity
import io.reactivex.Observable

class LoginRestApiImpl(val connectionUtils: ConnectionUtils) : LoginRestApi {

    override fun login(login: String, password: String) =
            Observable.create<UserAuthEntity> { emitter ->
                if (connectionUtils.isThereInternetConnection()) {
                    try {
                        val (request, response, result) = RestApiData.API_URL_SIGNIN.httpPost(generateParameters(login, password)).responseObject(UserAuthDeserializer())
                        if (result.component2() == null) {
                            emitter.onNext(result.get())
                            emitter.onComplete()
                        } else {
                            emitter.onError(NetworkConnectionException(result.component2()))
                        }
                    } catch (e: Exception) {
                        emitter.onError(NetworkConnectionException(e.cause))
                    }
                } else {
                    emitter.onError(NetworkConnectionException())
                }
            }

    private fun generateParameters(login: String, password: String) =
            listOf(Pair("login", login),
                    Pair("password", password))
}