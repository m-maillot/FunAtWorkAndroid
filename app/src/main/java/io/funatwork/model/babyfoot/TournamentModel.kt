package io.funatwork.model.babyfoot

import org.joda.time.DateTime

data class TournamentModel(val id: Int, val name: String, val startDate: DateTime, val rounds: List<RoundModel>)