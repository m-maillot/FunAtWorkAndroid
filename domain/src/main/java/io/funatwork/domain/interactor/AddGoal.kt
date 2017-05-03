package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.AddGoalParam
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.repository.GameRepository
import io.reactivex.Observable

class AddGoal(val gameRepository: GameRepository,
              override val threadExecutor: ThreadExecutor,
              override val postExecutionThread: PostExecutionThread) :
        UseCase<Game, AddGoalParam>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: AddGoalParam): Observable<Game> =
            gameRepository.addGoal(params.game.id, params.stricker, params.gamelle)
}