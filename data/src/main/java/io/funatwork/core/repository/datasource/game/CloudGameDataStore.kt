package io.funatwork.core.repository.datasource.game

import io.funatwork.core.entity.PlayerEntity
import io.funatwork.core.entity.babyfoot.GameEntity
import io.funatwork.core.entity.babyfoot.TeamEntity
import io.funatwork.core.net.game.GameRestApi
import io.funatwork.domain.model.babyfoot.GameMode
import io.reactivex.Observable

class CloudGameDataStore(val gameRestApi: GameRestApi) : GameDataStore {

    override fun createGame(redTeam: TeamEntity, blueTeam: TeamEntity, mode: GameMode, modeValueLimit: Int) =
            gameRestApi.startGame(redTeam, blueTeam, mode, modeValueLimit)

    override fun gameEntityList() =
            gameRestApi.gameEntityList()

    override fun gameEntity(gameId: Int) =
            gameRestApi.gameEntity(gameId)

    override fun addGoal(gameId: Int, striker: PlayerEntity, gamelle: Boolean) =
            gameRestApi.addGoal(gameId, striker, gamelle)

    override fun stopGame(gameId: Int, cancelled: Boolean) =
            gameRestApi.stopGame(gameId, cancelled)

    override fun currentGameEntity(): Observable<GameEntity> =
            gameRestApi.currentGameEntity()
}