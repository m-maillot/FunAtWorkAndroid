package io.funatwork.domain.repository

import io.funatwork.domain.model.babyfoot.Tournament
import io.reactivex.Observable

interface TournamentRepository {
    fun currentTournament(): Observable<Tournament>

}