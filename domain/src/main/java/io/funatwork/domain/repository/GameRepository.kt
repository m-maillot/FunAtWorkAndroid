package io.funatwork.domain.repository

import io.funatwork.domain.model.Player
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.model.babyfoot.Team
import io.reactivex.Observable

interface GameRepository {

    fun startGame(redTeam: Team, blueTeam: Team): Observable<Game>

    fun games(): Observable<List<Game>>

    fun game(gameId: Int): Observable<Game>

    fun addGoal(gameId: Int, striker: Player): Observable<Game>
}