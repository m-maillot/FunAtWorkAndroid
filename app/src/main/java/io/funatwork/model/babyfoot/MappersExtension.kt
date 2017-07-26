package io.funatwork.model.babyfoot

import io.funatwork.domain.model.babyfoot.*
import io.funatwork.model.toBo
import io.funatwork.model.toModel

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
                beginTimestampInSeconds = beginTimestampInSeconds,
                blueTeam = blueTeam.toModel(),
                redTeam = redTeam.toModel(),
                blueTeamGoal = blueTeamGoal,
                redTeamGoal = redTeamGoal,
                ended = ended,
                goals = goals.map(Goal::toModel),
                status = status)

fun GameModel.toBo() =
        Game(id = id,
                beginTimestampInSeconds = beginTimestampInSeconds,
                blueTeam = blueTeam.toBo(),
                redTeam = redTeam.toBo(),
                blueTeamGoal = blueTeamGoal,
                redTeamGoal = redTeamGoal,
                ended = ended,
                goals = goals.map(GoalModel::toBo),
                status = status)

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
                eloRanking = eloRanking,
                goalAverage = goalAverage,
                loose = loose,
                victory = victory)
