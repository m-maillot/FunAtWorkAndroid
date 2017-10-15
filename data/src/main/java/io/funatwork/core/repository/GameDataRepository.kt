package io.funatwork.core.repository

import io.funatwork.core.entity.babyfoot.toBo
import io.funatwork.core.entity.babyfoot.toEntity
import io.funatwork.core.entity.toEntity
import io.funatwork.core.repository.datasource.game.GameDataStoreFactory
import io.funatwork.domain.model.Player
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.model.babyfoot.GameMode
import io.funatwork.domain.model.babyfoot.Team
import io.funatwork.domain.repository.GameRepository
import io.reactivex.Observable

class GameDataRepository(val gameDataStoreFactory: GameDataStoreFactory) : GameRepository {

    override fun startGame(redTeam: Team, blueTeam: Team, mode: GameMode, modeValueLimit: Int): Observable<Game> =
            gameDataStoreFactory.create().createGame(redTeam.toEntity(), blueTeam.toEntity(), mode, modeValueLimit).map { it.toBo() }

    override fun games(): Observable<List<Game>> =
            gameDataStoreFactory.create().gameEntityList().map { it.map { it.toBo() } }

    override fun game(gameId: Int): Observable<Game> =
            gameDataStoreFactory.create().gameEntity(gameId).map { it.toBo() }

    override fun addGoal(gameId: Int, striker: Player, gamelle: Boolean) =
            gameDataStoreFactory.create().addGoal(gameId, striker.toEntity(), gamelle).map { it.toBo() }

    override fun stopGame(gameId: Int, cancelled: Boolean): Observable<Game> =
            gameDataStoreFactory.create().stopGame(gameId, cancelled).map { it.toBo() }

    override fun currentGame(): Observable<Game> =
            gameDataStoreFactory.create().currentGameEntity().map { it.toBo() }

}