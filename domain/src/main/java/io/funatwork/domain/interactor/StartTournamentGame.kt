package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.StartTournamentGameParam
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.repository.TournamentRepository
import io.reactivex.Observable

class StartTournamentGame(private val tournamentRepository: TournamentRepository,
                          override val threadExecutor: ThreadExecutor,
                          override val postExecutionThread: PostExecutionThread) :
        UseCase<Game, StartTournamentGameParam>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: StartTournamentGameParam): Observable<Game> =
            tournamentRepository.startGame(params.gameId)
}