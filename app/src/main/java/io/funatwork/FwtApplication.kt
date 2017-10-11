package io.funatwork

import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import io.funatwork.core.executor.JobExecutor
import io.funatwork.core.executor.SequentialJobExecutor
import io.funatwork.model.UserAuthModel
import io.funatwork.model.babyfoot.generateUnknownPlayer

class FwtApplication : MultiDexApplication() {

    val uiThread = UIThread()
    val jobExecutor = JobExecutor()
    val sequentialJobExecutor = SequentialJobExecutor()

    var connectedUser: UserAuthModel = UserAuthModel(generateUnknownPlayer())

    override fun onCreate() {
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }
    }
}