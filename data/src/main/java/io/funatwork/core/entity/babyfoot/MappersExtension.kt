package io.funatwork.core.entity.babyfoot

import io.funatwork.core.entity.toBo
import io.funatwork.core.entity.toEntity
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.domain.model.babyfoot.Goal
import io.funatwork.domain.model.babyfoot.Team

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
                beginTimestampInSeconds = beginTimestampInSeconds,
                blueTeam = blueTeam.toBo(),
                redTeam = redTeam.toBo(),
                blueTeamGoal = blueTeamGoal,
                redTeamGoal = redTeamGoal,
                ended = ended,
                goals = goals.map { it.toBo() },
                status = status)