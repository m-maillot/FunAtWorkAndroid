package io.funatwork.core.repository

import io.funatwork.core.entity.babyfoot.toBo
import io.funatwork.core.entity.babyfoot.toEntity
import io.funatwork.core.entity.toEntity
import io.funatwork.core.repository.datasource.GameDataStoreFactory
import io.funatwork.domain.model.Player
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.model.babyfoot.Team
import io.funatwork.domain.repository.GameRepository
import io.reactivex.Observable

class GameDataRepository(val gameDataStoreFactory: GameDataStoreFactory) : GameRepository {

    override fun startGame(redTeam: Team, blueTeam: Team): Observable<Game> =
            gameDataStoreFactory.create().createGame(redTeam.toEntity(), blueTeam.toEntity()).map { it.toBo() }

    override fun games(): Observable<List<Game>> =
            gameDataStoreFactory.create().gameEntityList().map { it.map { it.toBo() } }

    override fun game(gameId: Int): Observable<Game> =
            gameDataStoreFactory.create().gameEntity(gameId).map { it.toBo() }

    override fun addGoal(gameId: Int, striker: Player) =
            gameDataStoreFactory.create().addGoal(gameId, striker.toEntity()).map { it.toBo() }
}