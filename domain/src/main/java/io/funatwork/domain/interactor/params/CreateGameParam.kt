package io.funatwork.domain.interactor.params

import io.funatwork.domain.model.babyfoot.Team

data class CreateGameParam(val redTeam: Team, val blueTeam: Team)