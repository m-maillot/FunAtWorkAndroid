package io.funatwork.domain.interactor

import io.reactivex.observers.DisposableObserver

/**
 * Created by mmaillot on 4/14/17.
 */
open class DefaultObserver<T> : DisposableObserver<T>() {

    override fun onNext(element: T) {
        // no-op by default.
    }

    override fun onComplete() {
        // no-op by default.
    }

    override fun onError(exception: Throwable?) {
        // no-op by default.
    }
}