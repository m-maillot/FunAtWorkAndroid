package io.funatwork.view

import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.TeamModel
import io.funatwork.model.PlayerModel
import io.funatwork.model.Position
import io.funatwork.model.Team

interface StartGameView : LoadDataView {

    fun renderPlayerList(playerModelList: List<PlayerModel>)

    fun onSelectPlayer(player: PlayerModel, team: Team, position: Position)

    fun onReadyToStart(redTeam: TeamModel, blueTeam: TeamModel)

    fun onGameStarted(game: GameModel)

}