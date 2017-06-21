package io.funatwork.presenter

import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.GetPlayerList
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.Player
import io.funatwork.model.PlayerModel
import io.funatwork.model.Position
import io.funatwork.model.Team
import io.funatwork.model.babyfoot.TeamModel
import io.funatwork.model.toModel
import io.funatwork.view.SelectPlayersView
import io.funatwork.view.adapter.PlayerAdapter

class SelectPlayersPresenter(val selectPlayersView: SelectPlayersView, val getPlayerList: GetPlayerList) : Presenter, PlayerAdapter.OnItemClickListener {

    val undefinedPlayer = PlayerModel(-1, "unknown", "")
    var playerRedAttack = undefinedPlayer
    var playerRedDefense = undefinedPlayer
    var playerBlueAttack = undefinedPlayer
    var playerBlueDefense = undefinedPlayer

    override fun resume() {}

    override fun pause() {}

    override fun destroy() {}

    /**
     * Initializes the presenter by start retrieving the players list.
     */
    fun initialize() {
        this.loadPlayerList()
    }

    /**
     * Loads all users.
     */
    private fun loadPlayerList() {
        selectPlayersView.showLoading()
        getPlayerList.execute(PlayerListObserver(selectPlayersView), NoParams())
    }

    fun removePlayer(team: Team, position: Position) {
        val player: PlayerModel
        if (team == Team.RED) {
            if (position == Position.ATTACK) {
                player = playerRedAttack
                playerRedAttack = undefinedPlayer
            } else {
                player = playerRedDefense
                playerRedDefense = undefinedPlayer
            }
        } else {
            if (position == Position.ATTACK) {
                player = playerBlueAttack
                playerBlueAttack = undefinedPlayer
            } else {
                player = playerBlueDefense
                playerBlueDefense = undefinedPlayer
            }
        }
        selectPlayersView.onRemovePlayer(player, team, position)
    }

    fun setDefaultPlayerSelected(player: PlayerModel, team: Team, position: Position) {
        when (team) {
            Team.RED -> if (position == Position.ATTACK) playerRedAttack = player else playerRedDefense = player
            Team.BLUE -> if (position == Position.ATTACK) playerBlueAttack = player else playerBlueDefense = player
        }
        selectPlayersView.onSelectPlayer(player, team, position)
    }

    override fun onPlayerItemClicked(player: PlayerModel) {
        if (playerRedAttack.id == -1) {
            playerRedAttack = player
            selectPlayersView.onSelectPlayer(player, Team.RED, Position.ATTACK)
        } else if (playerRedDefense.id == -1) {
            playerRedDefense = player
            selectPlayersView.onSelectPlayer(player, Team.RED, Position.DEFENSE)
        } else if (playerBlueAttack.id == -1) {
            playerBlueAttack = player
            selectPlayersView.onSelectPlayer(player, Team.BLUE, Position.ATTACK)
        } else if (playerBlueDefense.id == -1) {
            playerBlueDefense = player
            selectPlayersView.onSelectPlayer(player, Team.BLUE, Position.DEFENSE)
        }
        if (playerBlueAttack.id != -1 && playerRedDefense.id != -1
                && playerRedAttack.id != -1 && playerBlueDefense.id != -1) {
            selectPlayersView.onReadyToStart(
                    TeamModel(attackPlayer = playerRedAttack, defensePlayer = playerRedDefense),
                    TeamModel(attackPlayer = playerBlueAttack, defensePlayer = playerBlueDefense))
        }
    }

    private class PlayerListObserver(val selectPlayersView: SelectPlayersView) : DefaultObserver<List<Player>>() {

        override fun onComplete() {
            selectPlayersView.hideLoading()
        }

        override fun onError(e: Throwable?) {
            selectPlayersView.hideLoading()
            selectPlayersView.showError(title = "Error happen", message = e?.message ?: "Unknown Error")
        }

        override fun onNext(players: List<Player>) {
            selectPlayersView.renderPlayerList(players.map(Player::toModel))
        }
    }
}