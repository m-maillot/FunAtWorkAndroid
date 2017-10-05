package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.babyfoot.Tournament
import io.funatwork.domain.repository.TournamentRepository

class GetCurrentTournament(val tournamentRepository: TournamentRepository,
                           override val threadExecutor: ThreadExecutor,
                           override val postExecutionThread: PostExecutionThread) : UseCase<Tournament, NoParams>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: NoParams) =
            tournamentRepository.currentTournament()
}