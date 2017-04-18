package io.funatwork.view

import io.funatwork.model.PlayerModel
import io.funatwork.model.Position
import io.funatwork.model.Team
import io.funatwork.model.babyfoot.TeamModel

interface SelectPlayersView : LoadDataView {

    fun renderPlayerList(playerModelList: List<PlayerModel>)

    fun onSelectPlayer(player: PlayerModel, team: Team, position: Position)

    fun onRemovePlayer(player: PlayerModel, team: Team, position: Position)

    fun onReadyToStart(redTeam: TeamModel, blueTeam: TeamModel)
}