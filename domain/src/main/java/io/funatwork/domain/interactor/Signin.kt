package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.LoginParam
import io.funatwork.domain.model.PlayerAuth
import io.funatwork.domain.repository.LoginRepository
import io.reactivex.Observable

class Signin(val loginRepository: LoginRepository,
             override val threadExecutor: ThreadExecutor,
             override val postExecutionThread: PostExecutionThread) :
        UseCase<PlayerAuth, LoginParam>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: LoginParam): Observable<PlayerAuth> =
            loginRepository.signin(params.login, params.password)
}