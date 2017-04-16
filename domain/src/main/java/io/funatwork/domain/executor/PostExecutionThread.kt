package io.funatwork.domain.executor

import io.reactivex.Scheduler

/**
 * Created by mmaillot on 4/14/17.
 */
interface PostExecutionThread {

    fun getScheduler(): Scheduler
}