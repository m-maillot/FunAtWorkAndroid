package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.repository.GameRepository
import io.reactivex.Observable

class GetGameList(val gameRepository: GameRepository,
                  override val threadExecutor: ThreadExecutor,
                  override val postExecutionThread: PostExecutionThread) :
        UseCase<List<Game>, NoParams>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: NoParams): Observable<List<Game>> =
            gameRepository.games()
}