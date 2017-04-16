package io.funatwork.domain.interactor

import io.funatwork.domain.executor.PostExecutionThread
import io.funatwork.domain.executor.ThreadExecutor
import io.funatwork.domain.model.Player
import io.funatwork.domain.repository.PlayerRepository
import io.reactivex.Observable

class GetPlayerList(val playerRepository: PlayerRepository,
                    override val threadExecutor: ThreadExecutor,
                    override val postExecutionThread: PostExecutionThread) :
        UseCase<List<Player>, Void>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Void): Observable<List<Player>> =
            playerRepository.players()
}