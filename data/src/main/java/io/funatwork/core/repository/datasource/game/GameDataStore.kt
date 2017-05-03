package io.funatwork.core.repository.datasource.game

import io.funatwork.core.entity.PlayerEntity
import io.funatwork.core.entity.babyfoot.GameEntity
import io.funatwork.core.entity.babyfoot.TeamEntity
import io.reactivex.Observable

interface GameDataStore {

    fun gameEntity(gameId: Int): Observable<GameEntity>

    fun gameEntityList(): Observable<List<GameEntity>>

    fun createGame(redTeam: TeamEntity, blueTeam: TeamEntity): Observable<GameEntity>

    fun addGoal(gameId: Int, striker: PlayerEntity, gamelle: Boolean): Observable<GameEntity>

    fun stopGame(gameId: Int, cancelled: Boolean): Observable<GameEntity>
}