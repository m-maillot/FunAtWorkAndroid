package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.NoParams
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class UseCase<T, in Params>(open val threadExecutor: ThreadExecutor, open val postExecutionThread: PostExecutionThread) {

    val disposables = CompositeDisposable()

    /**
     * Builds an [Observable] which will be used when executing the current [UseCase].
     */
    internal abstract fun buildUseCaseObservable(params: Params): Observable<T>

    /**
     * Executes the current use case.

     * @param observer [DisposableObserver] which will be listening to the observable build
     * * by [.buildUseCaseObservable] ()} method.
     * *
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    fun execute(observer: DisposableObserver<T>, params: Params) {
        val observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler())
        addDisposable(observable.subscribeWith(observer))
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}