package io.funatwork.core.executor

import io.funatwork.domain.executor.ThreadExecutor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class JobExecutor : ThreadExecutor {

    val threadPoolExecutor: ThreadPoolExecutor = ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS,
            LinkedBlockingQueue<Runnable>(), JobThreadFactory())

    override fun execute(command: Runnable) =
            threadPoolExecutor.execute(command)

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0

        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable, "android_" + counter++)
        }
    }
}