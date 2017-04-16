package io.funatwork.core.cache

import android.content.Context
import com.google.gson.reflect.TypeToken
import io.funatwork.core.cache.serializer.Serializer
import io.funatwork.core.entity.PlayerEntity
import io.funatwork.core.exception.PlayerNotFoundException
import io.funatwork.domain.executor.ThreadExecutor
import io.reactivex.Observable
import java.io.File
import java.util.*

class PlayerCacheImpl(val context: Context, val cacheDir: File, val serializer: Serializer<List<PlayerEntity>>, val fileManager: FileManager, val threadExecutor: ThreadExecutor) : PlayerCache {

    private val SETTINGS_FILE_NAME = "io.funatwork.SETTINGS"
    private val SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update"

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    override fun get(): Observable<List<PlayerEntity>> =
            Observable.create<List<PlayerEntity>> { emitter ->
                val fileContent = fileManager.readFileContent(buildFile())
                val playerEntity = serializer.deserialize(fileContent, object : TypeToken<ArrayList<PlayerEntity>>() {}.type)
                if (playerEntity != null) {
                    emitter.onNext(playerEntity)
                    emitter.onComplete()
                } else {
                    emitter.onError(PlayerNotFoundException())
                }
            }


    private fun buildFile() = File(cacheDir, "players.json")

    override fun put(players: List<PlayerEntity>) {
        val playersEntityFile = buildFile()
        val jsonString = serializer.serialize(players, object : TypeToken<ArrayList<PlayerEntity>>() {}.type)
        executeAsynchronously(CacheWriter(fileManager, playersEntityFile, jsonString))
        setLastCacheUpdateTimeMillis()
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = getLastCacheUpdateTimeMillis()

        val expired = currentTime - lastUpdateTime > EXPIRATION_TIME
        if (expired) {
            evict()
        }

        return expired
    }

    override fun evict() =
            executeAsynchronously(CacheEvictor(fileManager, cacheDir))

    private fun setLastCacheUpdateTimeMillis() =
            fileManager.writeToPreferences(context, SETTINGS_FILE_NAME,
                    SETTINGS_KEY_LAST_CACHE_UPDATE, System.currentTimeMillis())

    private fun getLastCacheUpdateTimeMillis() =
            fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                    SETTINGS_KEY_LAST_CACHE_UPDATE)


    private fun executeAsynchronously(runnable: Runnable) =
            threadExecutor.execute(runnable)

    private class CacheWriter(private val fileManager: FileManager, private val fileToWrite: File, private val fileContent: String) : Runnable {

        override fun run() {
            this.fileManager.writeToFile(fileToWrite, fileContent)
        }
    }

    private class CacheEvictor(private val fileManager: FileManager, private val cacheDir: File) : Runnable {

        override fun run() {
            fileManager.clearDirectory(cacheDir)
        }
    }
}