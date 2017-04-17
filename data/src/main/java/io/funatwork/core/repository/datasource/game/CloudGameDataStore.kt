package io.funatwork.core.repository.datasource

import io.funatwork.core.entity.PlayerEntity
import io.funatwork.core.entity.babyfoot.TeamEntity
import io.funatwork.core.net.player.GameRestApi

class CloudGameDataStore(val gameRestApi: GameRestApi) : GameDataStore {

    override fun createGame(redTeam: TeamEntity, blueTeam: TeamEntity) =
            gameRestApi.startGame(redTeam, blueTeam)

    override fun gameEntityList() =
            gameRestApi.gameEntityList()

    override fun gameEntity(gameId: Int) =
            gameRestApi.gameEntity(gameId)

    override fun addGoal(gameId: Int, striker: PlayerEntity) =
            gameRestApi.addGoal(gameId, striker)
}