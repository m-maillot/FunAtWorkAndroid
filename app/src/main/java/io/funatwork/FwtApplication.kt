package io.funatwork

import android.app.Application
import io.funatwork.core.executor.JobExecutor
import io.funatwork.core.executor.SequentialJobExecutor

class FwtApplication : Application() {

    val uiThread = UIThread()
    val jobExecutor = JobExecutor()
    val sequentialJobExecutor = SequentialJobExecutor()
}