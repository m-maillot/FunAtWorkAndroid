package io.funatwork.domain.interactor

import io.reactivex.observers.DisposableObserver

/**
 * Created by mmaillot on 4/14/17.
 */
open class DefaultObserver<T> : DisposableObserver<T>() {

    override fun onNext(t: T) {
        // no-op by default.
    }

    override fun onComplete() {
        // no-op by default.
    }

    override fun onError(e: Throwable?) {
        // no-op by default.
    }
}