package io.funatwork

import android.app.Application
import io.funatwork.core.executor.JobExecutor

class FwtApplication : Application() {

    val uiThread = UIThread()
    val jobExecutor = JobExecutor()
}