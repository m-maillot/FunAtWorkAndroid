package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.interactor.params.PlayerStatsParam
import io.funatwork.domain.model.babyfoot.PlayerStats
import io.funatwork.domain.repository.StatsRepository
import io.reactivex.Observable

class GetPlayerStats(private val statsRepository: StatsRepository,
                     override val threadExecutor: ThreadExecutor,
                     override val postExecutionThread: PostExecutionThread) :
        UseCase<PlayerStats, PlayerStatsParam>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: PlayerStatsParam): Observable<PlayerStats> =
            statsRepository.byPlayer(params.playerId)
}