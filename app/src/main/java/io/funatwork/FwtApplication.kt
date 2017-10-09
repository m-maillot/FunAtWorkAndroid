package io.funatwork

import android.app.Application
import io.funatwork.core.executor.JobExecutor
import io.funatwork.core.executor.SequentialJobExecutor
import io.funatwork.model.UserAuthModel
import io.funatwork.model.babyfoot.generateUnknownPlayer

class FwtApplication : Application() {

    val uiThread = UIThread()
    val jobExecutor = JobExecutor()
    val sequentialJobExecutor = SequentialJobExecutor()

    var connectedUser: UserAuthModel = UserAuthModel(generateUnknownPlayer())
}