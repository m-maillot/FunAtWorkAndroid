package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.babyfoot.PlayerStats
import io.funatwork.domain.repository.StatsRepository
import io.reactivex.Observable

class GetPlayerStats(val statsRepository: StatsRepository,
                     override val threadExecutor: ThreadExecutor,
                     override val postExecutionThread: PostExecutionThread) :
        UseCase<List<PlayerStats>, NoParams>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: NoParams): Observable<List<PlayerStats>> =
            statsRepository.byPlayers()
}