package io.funatwork.domain.model.babyfoot

import org.joda.time.DateTime

data class Tournament(val id: Int, val name: String, val startDate: DateTime, val rounds: List<Round>)