package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.babyfoot.TeamStats
import io.funatwork.domain.repository.StatsRepository
import io.reactivex.Observable

class GetTeamStats(val statsRepository: StatsRepository,
                   override val threadExecutor: ThreadExecutor,
                   override val postExecutionThread: PostExecutionThread) :
        UseCase<List<TeamStats>, NoParams>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: NoParams): Observable<List<TeamStats>> =
            statsRepository.byTeams()
}