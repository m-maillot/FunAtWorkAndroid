package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.CreateGameParam
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.repository.GameRepository
import io.reactivex.Observable

class StartGame(val gameRepository: GameRepository,
                override val threadExecutor: ThreadExecutor,
                override val postExecutionThread: PostExecutionThread) :
        UseCase<Game, CreateGameParam>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: CreateGameParam): Observable<Game> =
            gameRepository.startGame(params.redTeam, params.blueTeam, params.mode, params.modeLimitValue)
}