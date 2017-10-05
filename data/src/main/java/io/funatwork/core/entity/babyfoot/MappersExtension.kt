package io.funatwork.core.entity.babyfoot

import io.funatwork.core.entity.toBo
import io.funatwork.core.entity.toEntity
import io.funatwork.domain.model.babyfoot.*
import org.joda.time.format.ISODateTimeFormat.dateTimeNoMillis

fun TeamEntity.toBo() =
        Team(id = id,
                attackPlayer = attackPlayer.toBo(),
                defensePlayer = defensePlayer.toBo())

fun Team.toEntity() =
        TeamEntity(id = id,
                attackPlayer = attackPlayer.toEntity(),
                defensePlayer = defensePlayer.toEntity())

fun GoalEntity.toBo() =
        Goal(id = id,
                position = position,
                striker = stricker.toBo())

fun GameEntity.toBo() =
        Game(id = id,
                startedDate = startedDate?.let { dateTimeNoMillis().parseDateTime(it) },
                blueTeam = blueTeam?.toBo(),
                redTeam = redTeam?.toBo(),
                blueTeamGoal = blueTeamGoal,
                redTeamGoal = redTeamGoal,
                endedDate = endedDate?.let { dateTimeNoMillis().parseDateTime(it) },
                goals = goals.map { it.toBo() },
                status = status,
                plannedDate = plannedDate?.let { dateTimeNoMillis().parseDateTime(it) })

fun PlayerStatsEntity.toBo() =
        PlayerStats(player = player.toBo(),
                gamePlayed = gamePlayed,
                eloRanking = eloRanking,
                goalAverage = goalAverage,
                loose = loose,
                victory = victory)

fun TeamStatsEntity.toBo() =
        TeamStats(player1 = player1.toBo(),
                victory = victory,
                loose = loose,
                goalAverage = goalAverage,
                eloRanking = eloRanking,
                gamePlayed = gamePlayed,
                player2 = player2.toBo()
        )


fun TournamentEntity.toBo() =
        Tournament(id = id,
                name = name,
                rounds = rounds.map { it.toBo() },
                startDate = dateTimeNoMillis().parseDateTime(startDate))

fun RoundEntity.toBo() =
        Round(index = index, games = games.map { it.toBo() })