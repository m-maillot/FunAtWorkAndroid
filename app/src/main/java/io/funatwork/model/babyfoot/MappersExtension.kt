package io.funatwork.model.babyfoot

import io.funatwork.domain.model.babyfoot.*
import io.funatwork.model.PlayerModel
import io.funatwork.model.toBo
import io.funatwork.model.toModel
import org.joda.time.DateTime

fun Team.toModel() =
        TeamModel(id = id,
                attackPlayer = attackPlayer.toModel(),
                defensePlayer = defensePlayer.toModel())

fun Goal.toModel() =
        GoalModel(id = id,
                striker = striker.toModel(),
                position = position)

fun GoalModel.toBo() =
        Goal(id = id,
                striker = striker.toBo(),
                position = position)

fun Game.toModel() =
        GameModel(id = id,
                creator = creator.toModel(),
                startedDate = startedDate ?: DateTime(0L),
                blueTeam = blueTeam?.toModel() ?: generateUnknownTeam(),
                redTeam = redTeam?.toModel() ?: generateUnknownTeam(),
                blueTeamGoal = blueTeamGoal ?: -1,
                redTeamGoal = redTeamGoal ?: -1,
                endedDate = endedDate ?: DateTime(0L),
                goals = goals.map { it.toModel() },
                status = status,
                plannedDate = plannedDate ?: DateTime(0L),
                tournamentId = tournamentId ?: -1,
                mode = mode,
                modeLimitValue = modeLimitValue)

fun GameModel.toBo() =
        Game(id = id,
                creator = creator.toBo(),
                startedDate = startedDate,
                blueTeam = blueTeam.toBo(),
                redTeam = redTeam.toBo(),
                blueTeamGoal = blueTeamGoal,
                redTeamGoal = redTeamGoal,
                endedDate = endedDate,
                goals = goals.map(GoalModel::toBo),
                status = status,
                plannedDate = plannedDate,
                tournamentId = tournamentId,
                mode = mode,
                modeLimitValue = modeLimitValue)

fun TeamModel.toBo() =
        Team(id = id,
                attackPlayer = attackPlayer.toBo(),
                defensePlayer = defensePlayer.toBo())

fun TeamStats.toModel() =
        TeamStatsModel(player1 = player1.toModel(),
                player2 = player2.toModel(),
                gamePlayed = gamePlayed,
                eloRanking = eloRanking,
                goalAverage = goalAverage,
                loose = loose,
                victory = victory)

fun PlayerStats.toModel() =
        PlayerStatsModel(player = player.toModel(),
                gamePlayed = gamePlayed,
                eloRanking = eloRanking.toInt(),
                goalAverage = goalAverage,
                loose = loose,
                victory = victory,
                goals = goals,
                rank = rank)

fun Tournament.toModel() =
        TournamentModel(id = id,
                startDate = startDate,
                name = name,
                rounds = rounds.map { it.toModel() })

fun Round.toModel() =
        RoundModel(index = index, games = games.map { it.toModel() })


fun generateUnknownTeam() = TeamModel(id = -1,
        attackPlayer = generateUnknownPlayer(),
        defensePlayer = generateUnknownPlayer())

fun generateUnknownPlayer() = PlayerModel(-1, "", "", "")