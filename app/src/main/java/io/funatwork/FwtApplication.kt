package io.funatwork

import android.app.Application
import io.funatwork.core.cache.AccountCacheImpl
import io.funatwork.core.cache.FileManager
import io.funatwork.core.cache.serializer.Serializer
import io.funatwork.core.entity.toBo
import io.funatwork.core.executor.JobExecutor
import io.funatwork.core.executor.SequentialJobExecutor
import io.funatwork.model.UserAuthModel
import io.funatwork.model.toModel

class FwtApplication : Application() {

    val uiThread = UIThread()
    val jobExecutor = JobExecutor()
    val sequentialJobExecutor = SequentialJobExecutor()

    override fun onCreate() {
        super.onCreate()
    }
}