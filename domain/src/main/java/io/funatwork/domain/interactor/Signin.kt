package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.LoginParam
import io.funatwork.domain.model.UserAuth
import io.funatwork.domain.repository.AccountRepository
import io.reactivex.Observable

class Signin(val accountRepository: AccountRepository,
             override val threadExecutor: ThreadExecutor,
             override val postExecutionThread: PostExecutionThread) :
        UseCase<UserAuth, LoginParam>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: LoginParam): Observable<UserAuth> =
            accountRepository.signin(params.login, params.password)
}