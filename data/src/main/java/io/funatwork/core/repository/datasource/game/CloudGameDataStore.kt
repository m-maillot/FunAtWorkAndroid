package io.funatwork.core.repository.datasource.game

import io.funatwork.core.entity.PlayerEntity
import io.funatwork.core.entity.UserAuthEntity
import io.funatwork.core.entity.babyfoot.TeamEntity
import io.funatwork.core.net.game.GameRestApi

class CloudGameDataStore(val gameRestApi: GameRestApi) : GameDataStore {

    override fun createGame(redTeam: TeamEntity, blueTeam: TeamEntity) =
            gameRestApi.startGame(redTeam, blueTeam)

    override fun gameEntityList() =
            gameRestApi.gameEntityList()

    override fun gameEntity(gameId: Int) =
            gameRestApi.gameEntity(gameId)

    override fun addGoal(gameId: Int, striker: PlayerEntity, gamelle: Boolean) =
            gameRestApi.addGoal(gameId, striker, gamelle)

    override fun stopGame(gameId: Int, cancelled: Boolean) =
            gameRestApi.stopGame(gameId, cancelled)
}