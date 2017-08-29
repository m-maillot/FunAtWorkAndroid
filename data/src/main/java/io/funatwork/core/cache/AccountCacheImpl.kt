package io.funatwork.core.cache

import com.google.gson.reflect.TypeToken
import io.funatwork.core.cache.serializer.Serializer
import io.funatwork.core.entity.UserAuthEntity
import io.funatwork.core.exception.NotLoggedException
import io.reactivex.Observable
import java.io.File


/**
 * Created by mmaillot on 7/27/17.
 */
class AccountCacheImpl(private val cacheDir: File,
                       private val serializer: Serializer<UserAuthEntity>,
                       private val fileManager: FileManager) : AccountCache {

    override fun put(userAuthEntity: UserAuthEntity) =
            fileManager.writeToFile(buildFile(), serializer.serialize(userAuthEntity, object : TypeToken<UserAuthEntity>() {}.type))

    override fun get() =
            Observable.create<UserAuthEntity> { emitter ->
                val fileContent = fileManager.readFileContent(buildFile())
                val userAuth = serializer.deserialize(fileContent, object : TypeToken<UserAuthEntity>() {}.type)
                if (userAuth != null) {
                    emitter.onNext(userAuth)
                    emitter.onComplete()
                } else {
                    emitter.onError(NotLoggedException())
                }
            }

    override fun isExpired() =
            getAuthUser()?.expire_at?.let {
                it > System.currentTimeMillis()
            } ?: false

    override fun evict() {
        buildFile().delete()
    }

    private fun getAuthUser() =
            serializer.deserialize(fileManager.readFileContent(buildFile()), object : TypeToken<UserAuthEntity>() {}.type)

    private fun buildFile() = File(cacheDir, "user_authentication.json")

}