package io.funatwork.core.entity.babyfoot

data class TournamentEntity(val id: Int, val name: String, val startDate: String, val rounds: List<RoundEntity>)