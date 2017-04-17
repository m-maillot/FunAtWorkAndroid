package io.funatwork.core.net.player

import io.funatwork.core.entity.PlayerEntity
import io.funatwork.core.entity.babyfoot.GameEntity
import io.funatwork.core.entity.babyfoot.TeamEntity
import io.reactivex.Observable

interface GameRestApi {

    fun gameEntity(gameId: Int): Observable<GameEntity>

    fun gameEntityList(): Observable<List<GameEntity>>

    fun startGame(redTeam: TeamEntity, blueTeam: TeamEntity): Observable<GameEntity>

    fun addGoal(gameId: Int, striker: PlayerEntity): Observable<GameEntity>
}