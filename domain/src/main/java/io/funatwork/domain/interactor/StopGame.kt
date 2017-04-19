package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.StopGameParam
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.repository.GameRepository
import io.reactivex.Observable

class StopGame(val gameRepository: GameRepository,
               override val threadExecutor: ThreadExecutor,
               override val postExecutionThread: PostExecutionThread) :
        UseCase<Game, StopGameParam>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: StopGameParam): Observable<Game> =
            gameRepository.stopGame(params.gameId, params.cancelled)
}