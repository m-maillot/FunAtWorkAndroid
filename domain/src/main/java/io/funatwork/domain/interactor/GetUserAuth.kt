package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.UserAuth
import io.funatwork.domain.repository.AccountRepository
import io.reactivex.Observable

class GetUserAuth(private val accountRepository: AccountRepository,
                  override val threadExecutor: ThreadExecutor,
                  override val postExecutionThread: PostExecutionThread) :
        UseCase<UserAuth, NoParams>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: NoParams): Observable<UserAuth> =
            accountRepository.load()
}