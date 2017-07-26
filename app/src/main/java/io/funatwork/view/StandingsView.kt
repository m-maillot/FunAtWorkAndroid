package io.funatwork.view

import io.funatwork.model.babyfoot.PlayerStatsModel
import io.funatwork.model.babyfoot.TeamStatsModel

interface StandingsView : LoadDataView {

    fun renderTeamStats(teamStatsList: List<TeamStatsModel>)

    fun renderPlayerStats(playerStatsList: List<PlayerStatsModel>)
}