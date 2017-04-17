package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.repository.GameRepository
import io.reactivex.Observable

class LoadGame(val gameRepository: GameRepository,
               override val threadExecutor: ThreadExecutor,
               override val postExecutionThread: PostExecutionThread) :
        UseCase<Game, Int>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Int): Observable<Game> =
            gameRepository.game(params)
}